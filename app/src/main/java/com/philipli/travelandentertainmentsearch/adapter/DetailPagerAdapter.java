package com.philipli.travelandentertainmentsearch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.bean.DetailResponse;
import com.philipli.travelandentertainmentsearch.test.SimpleFragment;
import com.philipli.travelandentertainmentsearch.ui.Detail.InfoFragment;
import com.philipli.travelandentertainmentsearch.ui.Detail.MapFragment;
import com.philipli.travelandentertainmentsearch.ui.Detail.PhotoFragment;
import com.philipli.travelandentertainmentsearch.ui.Detail.ReviewFragment;
import com.philipli.travelandentertainmentsearch.ui.Home.SearchFragment;

import java.util.List;

/**
 * Created by philipli on 2018/4/2.
 */

public class DetailPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] titles;


    public DetailPagerAdapter(FragmentManager fm, Context context, Place place, List<Bitmap> list, DetailResponse response) {
        super(fm);
        fragments = new Fragment[4];
        titles = new String[]{"INFO","PHOTOS", "MAP", "REVIEWS"};




        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    String website = "";
                    if (place.getWebsiteUri() != null) {
                        website = place.getWebsiteUri().toString();
                    }
                    fragments[i] = InfoFragment.getInstance(place.getPhoneNumber().toString(), place.getAddress().toString(),
                            place.getPriceLevel(), place.getRating(), response.getResult().getUrl(), website);
                    break;
                case 1:
                    fragments[i] = PhotoFragment.getInstance();
                    break;
                case 2:
                    fragments[i] = MapFragment.getInstance(place.getLatLng().latitude, place.getLatLng().longitude);
                    break;
                default:
                    fragments[i] = ReviewFragment.getInstance(response);
                    break;
            }

        }

    }

    public View getCustomView(Context context, int position) {
        View mView = LayoutInflater.from(context).inflate(R.layout.item_custom_tab, null);
        TextView mTextView = (TextView) mView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) mView.findViewById(R.id.imageView);
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.info_outline);
                break;
            case 1:
                imageView.setImageResource(R.drawable.photos);
                break;
            case 2:
                imageView.setImageResource(R.drawable.maps);
                break;
            case 3:
                imageView.setImageResource(R.drawable.review);
                break;
            default:
                break;
        }
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setText(titles[position]);
        return mView;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
