package com.philipli.travelandentertainmentsearch.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.bean.Favourite;
import com.philipli.travelandentertainmentsearch.bean.NearbySearchResponse;
import com.philipli.travelandentertainmentsearch.ui.Detail.DetailActivity;
import com.philipli.travelandentertainmentsearch.util.ConstantUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipli on 2018/4/3.
 */

public class PlaceListAdapter extends RecyclerView.Adapter {

    private List<NearbySearchResponse.NearbyPlace> mList = new ArrayList<>();
    private Context mContext;

    public PlaceListAdapter(Context context, List<NearbySearchResponse.NearbyPlace> list) {
        mList.addAll(list);
        mContext = context;
    }

    public void update(List<NearbySearchResponse.NearbyPlace> list) {
        mList.clear();
        mList.addAll(list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlaceListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PlaceListViewHolder placeListViewHolder = (PlaceListViewHolder) holder;
        NearbySearchResponse.NearbyPlace place = mList.get(position);
        placeListViewHolder.setItems(place.getName(), place.getVicinity(), place.getIcon(), mContext, place.getPlace_id());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class PlaceListViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView mTitle;
        private TextView mSubtitle;
        private ImageView favour;
        private LinearLayout linearLayout;

        public PlaceListViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            mTitle = itemView.findViewById(R.id.place_name);
            mSubtitle = itemView.findViewById(R.id.place_vicinity);
            favour = itemView.findViewById(R.id.favourite);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }

        public void setItems(final String title, final String subtitle, final String iconUrl, final Context context, final String placeId) {
            mTitle.setText(title);
            mSubtitle.setText(subtitle);
            Picasso.get().load(iconUrl).into(icon);
            if (ConstantUtil.getFavourite().contains(placeId)) {
                favour.setImageResource(R.drawable.heart_fill_red);
            }
            favour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ConstantUtil.getFavourite().contains(placeId)) {
                        ConstantUtil.removeFavourite(placeId);
                        ConstantUtil.removeFavouriteList(placeId);
                        ConstantUtil.updateFavourite();
                        favour.setImageResource(R.drawable.heart_outline_black);
                        Toast.makeText(context, title + " has been removed from Favourites.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ConstantUtil.addFavourite(placeId);
                        ConstantUtil.addFavouriteList(new Favourite(placeId, title, subtitle, iconUrl));
                        ConstantUtil.updateFavourite();
                        favour.setImageResource(R.drawable.heart_fill_red);
                        Toast.makeText(context, title + " has been added to Favourites.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    DetailActivity.start(context, placeId, title, iconUrl);
                }
            });
        }





    }
}
