package com.philipli.travelandentertainmentsearch.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.test.SimpleFragment;
import com.philipli.travelandentertainmentsearch.ui.Favourite.FavouriteFragment;
import com.philipli.travelandentertainmentsearch.ui.Home.SearchFragment;

/**
 * Created by philipli on 2018/4/2.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {

    private Fragment[] fragments;
    private String[] titles;


    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments = new Fragment[]{SearchFragment.getInstance(), FavouriteFragment.getInstance()};
        titles = new String[]{"SEARCH","FAVORITES"};
    }

    public View getCustomView(Context context, int position) {
        View mView = LayoutInflater.from(context).inflate(R.layout.item_custom_tab, null);
        TextView mTextView = (TextView) mView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) mView.findViewById(R.id.imageView);
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.search);
                break;
            case 1:
                imageView.setImageResource(R.drawable.heart_fill_white);
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
