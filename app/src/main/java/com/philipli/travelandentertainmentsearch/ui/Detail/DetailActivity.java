package com.philipli.travelandentertainmentsearch.ui.Detail;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResponse;
import com.google.android.gms.location.places.PlacePhotoResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.DetailPagerAdapter;
import com.philipli.travelandentertainmentsearch.adapter.HomePagerAdapter;
import com.philipli.travelandentertainmentsearch.adapter.PlaceListAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseActivity;
import com.philipli.travelandentertainmentsearch.bean.DetailResponse;
import com.philipli.travelandentertainmentsearch.bean.Favourite;
import com.philipli.travelandentertainmentsearch.network.VolleyRequest;
import com.philipli.travelandentertainmentsearch.ui.Result.ListActivity;
import com.philipli.travelandentertainmentsearch.util.ConstantUtil;
import com.philipli.travelandentertainmentsearch.util.ImageCache;
import com.philipli.travelandentertainmentsearch.util.PlaceCache;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by philipli on 2018/4/4.
 */

public class DetailActivity extends RxBaseActivity {

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public static final String PLACE_ID = "PLACE_ID";
    public static final String PLACE_NAME = "PLACE_NAME";
    public static final String ICON_URL = "ICON_URL";
    public static final String TAG = "DetailActivity";


    private DetailPagerAdapter adapter;
    private Place mPlace = null;
    private ProgressDialog progressDialog;
    private String placeId = "";
    private String placeName = "";
    private String iconUrl = "";
    private GeoDataClient mClient;
    private List<Bitmap> photoList = new ArrayList<>();
    private Menu menu;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        progressDialog = ProgressDialog.show(this, "", "Fetching results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        placeId = bundle.getString(PLACE_ID);
        placeName = bundle.getString(PLACE_NAME);
        iconUrl = bundle.getString(ICON_URL);
        setTitle(placeName);

        load(placeId);

    }

    private void load(String id) {
        mClient = Places.getGeoDataClient(this);
        Task<PlaceBufferResponse> task = mClient.getPlaceById(id);
        task.addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "place retrieve successful");
                    PlaceBufferResponse response = task.getResult();
                    mPlace = response.get(0).freeze();
                    PlaceCache.setPlace(mPlace);
                    response.release();
                    load2(placeId);

                }
                else {
                    Log.d(TAG, "place retrieve unsuccessful");
                }
            }
        });

    }

    private void load2(String placeId) {


        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = mClient.getPlacePhotos(placeId);
        photoMetadataResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoMetadataResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlacePhotoMetadataResponse> task) {
                // Get the list of photos.
                PlacePhotoMetadataResponse photos = task.getResult();
                // Get the PlacePhotoMetadataBuffer (metadata for all of the photos).
                final PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

                if (photoMetadataBuffer.getCount() == 0) {
                    ImageCache.setList(photoList);
                    load3();
                    return;
                }

                for (PlacePhotoMetadata photoMetadata : photoMetadataBuffer) {
                    // Get a full-size bitmap for the photo.
                    Task<PlacePhotoResponse> photoResponse = mClient.getPhoto(photoMetadata);
                    photoResponse.addOnCompleteListener(new OnCompleteListener<PlacePhotoResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<PlacePhotoResponse> task) {
                            PlacePhotoResponse photo = task.getResult();
                            Bitmap bitmap = photo.getBitmap();
                            photoList.add(bitmap);
                            if (photoList.size() == photoMetadataBuffer.getCount()) {
                                ImageCache.setList(photoList);
                                load3();
                            }
                        }
                    });

                }

            }
        });

    }

    private void load3() {
        String url = VolleyRequest.GOOGLE_URL;
        url += "placeid=" + placeId;
        url += "&key=" + ConstantUtil.DDF;

        VolleyRequest.detailService(url, this);
    }

    public void finishTask(DetailResponse response) {
        adapter =  new DetailPagerAdapter(getSupportFragmentManager(), getApplicationContext(),mPlace, photoList, response);
//        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(5);
        mSlidingTabs.setupWithViewPager(mViewPager);
        for (int i = 0; i < mSlidingTabs.getTabCount(); i++) {
            View customView = adapter.getCustomView(getApplicationContext(), i);
            mSlidingTabs.getTabAt(i).setCustomView(customView);
        }
        mSlidingTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        progressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.right_menu, menu);
        this.menu = menu;

        if (ConstantUtil.getFavourite().contains(placeId)) {
            menu.getItem(1).setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_red));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.action_share:
                String url2 = "https://twitter.com/intent/tweet?";
                String website = mPlace.getWebsiteUri() == null ? "" : mPlace.getWebsiteUri().toString();
                url2 += "text=" + "Check%20out%20" + placeName + "%20located%20at%20" + mPlace.getAddress().toString() + ".%20Website:%20" + website;
                url2 += "&hashtags=TravelAndEntertainmentSearch";
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                startActivity(browserIntent2);
                return true;
            case R.id.action_favourite:
                if (!ConstantUtil.getFavourite().contains(placeId)) {
                    ConstantUtil.addFavourite(placeId);
                    ConstantUtil.addFavouriteList(new Favourite(placeId, placeName, mPlace.getAddress().toString(), iconUrl));
                    ConstantUtil.updateFavourite();
                    Toast.makeText(getApplicationContext(), placeName + " has been added to from Favourites.", Toast.LENGTH_SHORT).show();
                    item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_red));
                }
                else {
                    ConstantUtil.removeFavourite(placeId);
                    ConstantUtil.removeFavouriteList(placeId);
                    ConstantUtil.updateFavourite();
                    Toast.makeText(getApplicationContext(), placeName + " has been removed from Favourites.", Toast.LENGTH_SHORT).show();
                    item.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.heart_fill_white));
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }


    public static void  start(Context context, String placeId, String placeName, String iconUrl) {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PLACE_ID, placeId);
        bundle.putString(PLACE_NAME, placeName);
        bundle.putString(ICON_URL, iconUrl);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, null);
    }
}
