package com.philipli.travelandentertainmentsearch.ui.Favourite;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.FavouriteListAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;
import com.philipli.travelandentertainmentsearch.bean.Favourite;
import com.philipli.travelandentertainmentsearch.ui.Result.ListActivity;
import com.philipli.travelandentertainmentsearch.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.philipli.travelandentertainmentsearch.util.ConstantUtil.getFavouriteList;

/**
 * Created by philipli on 2018/4/6.
 */

public class FavouriteFragment extends RxBaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty)
    TextView mEmpty;

    private FavouriteListAdapter adapter;




    @Override
    public int getLayoutId() {
        return R.layout.layout_favourite_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {

        List<Favourite> list = ConstantUtil.getFavouriteList();
        if(list.size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
        ConstantUtil.empty = mEmpty;
        adapter = new FavouriteListAdapter(getApplicationContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

    }

    public static FavouriteFragment getInstance() {
        return new FavouriteFragment();
    }

}
