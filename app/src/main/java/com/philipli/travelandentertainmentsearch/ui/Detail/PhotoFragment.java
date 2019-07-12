package com.philipli.travelandentertainmentsearch.ui.Detail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.PhotoAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;
import com.philipli.travelandentertainmentsearch.util.ImageCache;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by philipli on 2018/4/4.
 */

public class PhotoFragment extends RxBaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.photo_empty)
    TextView mEmpty;

    private PhotoAdapter adapter;
    private RecyclerView.LayoutManager manager;

    public static final String BITMAP = "BITMAP";



    @Override
    public int getLayoutId() {
        return R.layout.layout_photo_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {
        PhotoAdapter adapter = new PhotoAdapter(getApplicationContext());
        manager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        if (ImageCache.getList().size() > 0) {
            adapter.update(ImageCache.getList());
            adapter.notifyDataSetChanged();
        }
        else {
            mEmpty.setText("No Photos");
            mEmpty.setVisibility(View.VISIBLE);
        }



    }

    public static PhotoFragment getInstance() {
        PhotoFragment photoFragment = new PhotoFragment();

        return photoFragment;
    }

}
