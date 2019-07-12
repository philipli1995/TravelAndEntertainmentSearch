package com.philipli.travelandentertainmentsearch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.TravelEntertainmentSearchApp;
import com.philipli.travelandentertainmentsearch.bean.DetailResponse;
import com.philipli.travelandentertainmentsearch.bean.YelpReviewResponse;
import com.philipli.travelandentertainmentsearch.util.ReviewComparator;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by philipli on 2018/4/3.
 */

public class ReviewAdapter extends RecyclerView.Adapter {

    private List<DetailResponse.DetailResult.Review> mList = new ArrayList<>();
    private List<DetailResponse.DetailResult.Review> protoGoogleList = new ArrayList<>();
    private List<DetailResponse.DetailResult.Review> protoYelpList = new ArrayList<>();
    private int pointer = 0;
    private Context mContext;
    private TextView empty;

    public ReviewAdapter(Context context, List<DetailResponse.DetailResult.Review> list, TextView textView) {
        mList.addAll(list);
        protoGoogleList.addAll(list);
        mContext = context;
        empty = textView;
    }

    public void update(List<DetailResponse.DetailResult.Review> list) {
        mList.addAll(list);
    }

    public void addYelpReview(List<DetailResponse.DetailResult.Review> list) {
        protoYelpList.addAll(list);
    }

    public void onTypeSelected(int position) {
        if (position == pointer) {
            return;
        }
        pointer = position;
        if (position == 0) {
            mList.clear();
            mList.addAll(protoGoogleList);
        }
        else {
            mList.clear();
            mList.addAll(protoYelpList);
        }
        if (mList.size() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
        else {
            empty.setVisibility(View.INVISIBLE);
        }
    }

    public void onOrderSelected(int position) {
        if (position > 4 || position < 0) {
            return;
        }
        if (position == 0) {
            mList.clear();
            if (pointer == 0) {
                mList.addAll(protoGoogleList);
            }
            else {
                mList.addAll(protoYelpList);
            }
            return;
        }
        Collections.sort(mList, ReviewComparator.comparators[position-1]);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ReviewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
        reviewViewHolder.setItem(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class ReviewViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name;
        private TextView time;
        private TextView detail;
        private RatingBar ratingBar;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            icon =  itemView.findViewById(R.id.icon);
            name =  itemView.findViewById(R.id.author_name);
            time =  itemView.findViewById(R.id.time);
            detail =  itemView.findViewById(R.id.detail);
            ratingBar =  itemView.findViewById(R.id.rating);
        }

        public void setItem(DetailResponse.DetailResult.Review review) {
            Picasso.get().load(review.getProfile_photo_url()).fit().centerCrop().into(icon);
            name.setText(review.getAuthor_name());
            detail.setText(review.getText());
            ratingBar.setRating(review.getRating());
            Date date = new Date(review.getTime()*1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            DateFormat sdf = DateFormat.getDateInstance();
            sdf.setTimeZone(java.util.TimeZone.getDefault());
            String formattedDate = sdf.format(date);
            time.setText(formattedDate);


        }


    }
}
