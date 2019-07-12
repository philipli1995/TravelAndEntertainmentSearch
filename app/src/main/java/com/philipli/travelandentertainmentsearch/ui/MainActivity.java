package com.philipli.travelandentertainmentsearch.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.HomePagerAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseActivity;
import com.philipli.travelandentertainmentsearch.ui.Home.HomepageFragment;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity {


    HomepageFragment mHomepageFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        setTitle(R.string.app_title);

        mHomepageFragment = HomepageFragment.getInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, mHomepageFragment)
                .show(mHomepageFragment).commit();



    }

}
