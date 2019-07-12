package com.philipli.travelandentertainmentsearch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.bean.NearbySearchResponse;
import com.philipli.travelandentertainmentsearch.ui.Detail.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipli on 2018/4/3.
 */

public class PhotoAdapter extends RecyclerView.Adapter {

    private List<Bitmap> mList = new ArrayList<>();
    private Context mContext;

    public PhotoAdapter(Context context) {
        mContext = context;
    }

    public void update(List<Bitmap> list) {
        mList.addAll(list);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PhotoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        Bitmap bitmap = mList.get(position);
        photoViewHolder.setImage(bitmap);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            image =  itemView.findViewById(R.id.photo);
        }

        public void setImage(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }


    }
}
