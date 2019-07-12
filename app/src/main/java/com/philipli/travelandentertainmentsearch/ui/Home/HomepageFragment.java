package com.philipli.travelandentertainmentsearch.ui.Home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.HomePagerAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by philipli on 2018/4/2.
 */

public class HomepageFragment extends RxBaseFragment {

    @BindView(R.id.sliding_tabs)
    TabLayout mSlidingTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    HomePagerAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.layout_home_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {

        adapter =  new HomePagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mSlidingTabs.setupWithViewPager(mViewPager);
        for (int i = 0; i < mSlidingTabs.getTabCount(); i++) {
            View customView = adapter.getCustomView(getApplicationContext(), i);
            mSlidingTabs.getTabAt(i).setCustomView(customView);
        }

    }

    public static HomepageFragment getInstance() {
        return new HomepageFragment();
    }


}
