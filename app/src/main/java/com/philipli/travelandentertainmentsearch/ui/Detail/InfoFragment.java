package com.philipli.travelandentertainmentsearch.ui.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by philipli on 2018/4/4.
 */

public class InfoFragment extends RxBaseFragment {
    @BindView(R.id.info_address)
    TextView mInfoAddress;
    @BindView(R.id.info_phone_number)
    TextView mInfoPhoneNumber;
    @BindView(R.id.info_price)
    TextView mInfoPrice;
    @BindView(R.id.rating)
    RatingBar mRating;
    @BindView(R.id.info_google_page)
    TextView mInfoGooglePage;
    @BindView(R.id.info_website)
    TextView mInfoWebsite;

    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String ADDRESS = "ADDRESS";
    public static final String PRICE = "PRICE";
    public static final String RATE = "RATE";
    public static final String GOOGLE_URL = "GOOGLE_URL";
    public static final String WEBSITE = "WEBSITE";

    @Override
    public int getLayoutId() {
        return R.layout.layout_info_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {

        Bundle args = getArguments();
        mInfoAddress.setText(args.getString(ADDRESS));
        mInfoPhoneNumber.setText(args.getString(PHONE_NUMBER));
        mInfoPrice.setText(getPrice(args.getInt(PRICE)));
        mRating.setRating(args.getFloat(RATE));
        mInfoGooglePage.setText(args.getString(GOOGLE_URL));
        mInfoWebsite.setText(args.getString(WEBSITE));
    }

    private String getPrice(int price) {
        String ret = "";
        for (int i = 0; i < price; i++) {
            ret += "$";
        }
        return ret;
    }


    @OnClick({R.id.info_phone_number, R.id.info_google_page, R.id.info_website})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.info_phone_number:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mInfoPhoneNumber.getText().toString()));
                startActivity(intent);

                break;
            case R.id.info_google_page:
                String url2 = mInfoGooglePage.getText().toString();
                if (url2.startsWith("http://") || url2.startsWith("https://")) {
                    url2 = "http://" + url2;
                }
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                startActivity(browserIntent2);
                break;
            case R.id.info_website:
                String url = mInfoWebsite.getText().toString();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                break;

        }
    }

    public static InfoFragment getInstance(String phoneNumber, String address, int price, float rate, String googlePage, String website) {
        InfoFragment infoFragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(PHONE_NUMBER, phoneNumber);
        args.putString(ADDRESS, address);
        args.putInt(PRICE, price);
        args.putFloat(RATE, rate);
        args.putString(GOOGLE_URL, googlePage);
        args.putString(WEBSITE, website);
        infoFragment.setArguments(args);
        return infoFragment;
    }
}
