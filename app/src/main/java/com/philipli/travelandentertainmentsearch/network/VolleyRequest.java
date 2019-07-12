package com.philipli.travelandentertainmentsearch.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.philipli.travelandentertainmentsearch.bean.DetailResponse;
import com.philipli.travelandentertainmentsearch.bean.NearbySearchResponse;
import com.philipli.travelandentertainmentsearch.bean.YelpReviewResponse;
import com.philipli.travelandentertainmentsearch.ui.Detail.DetailActivity;
import com.philipli.travelandentertainmentsearch.ui.Detail.ReviewFragment;
import com.philipli.travelandentertainmentsearch.ui.Home.SearchFragment;
import com.philipli.travelandentertainmentsearch.ui.Result.ListActivity;

import java.text.ParseException;
import java.util.List;

/**
 * Created by philipli on 2018/4/3.
 */

public class VolleyRequest {

    public static final String BACKEND_URL = "https://my-nodejs-project-197106.appspot.com?";
    public static final  String GOOGLE_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    public static void nearbySearchService(String url, final Context context, final SearchFragment searchFragment) {

        RequestQueue queue = Volley.newRequestQueue(context);
        GsonRequest<NearbySearchResponse> myReq = new GsonRequest<NearbySearchResponse>(
                url,
                NearbySearchResponse.class,
                null,
                new Response.Listener<NearbySearchResponse>() {
                    @Override
                    public void onResponse(NearbySearchResponse response) {

//                        Log.d("results: ", response.toString());
                        searchFragment.onFinishSearch();
                        ListActivity.start(context, response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error: ", "");
                        searchFragment.onFinishSearch();

                    }
                });

        queue.add(myReq);
    }

    public static void nextPageService(String url, final ListActivity listActivity) {
        RequestQueue queue = Volley.newRequestQueue(listActivity);
        GsonRequest<NearbySearchResponse> myReq = new GsonRequest<NearbySearchResponse>(
                url,
                NearbySearchResponse.class,
                null,
                new Response.Listener<NearbySearchResponse>() {
                    @Override
                    public void onResponse(NearbySearchResponse response) {

//                        Log.d("results: ", response.toString());
                        listActivity.onNextPage(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error: ", "");
                        listActivity.onErrorPage();

                    }
                });

        queue.add(myReq);

    }

    public static void detailService(String url, final DetailActivity detailActivity) {
        RequestQueue queue = Volley.newRequestQueue(detailActivity);
        GsonRequest<DetailResponse> myReq = new GsonRequest<DetailResponse>(
                url,
                DetailResponse.class,
                null,
                new Response.Listener<DetailResponse>() {
                    @Override
                    public void onResponse(DetailResponse response) {

//                        Log.d("results: ", response.toString());
                        detailActivity.finishTask(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error: ", "");
                        detailActivity.finishTask(null);

                    }
                });

        queue.add(myReq);

    }

    public static void yelpService(String url, final ReviewFragment reviewFragment) {
        RequestQueue queue = Volley.newRequestQueue(reviewFragment.getApplicationContext());
        GsonRequest<YelpReviewResponse> myReq = new GsonRequest<YelpReviewResponse>(
                url,
                YelpReviewResponse.class,
                null,
                new Response.Listener<YelpReviewResponse>() {
                    @Override
                    public void onResponse(YelpReviewResponse response) {
                        try {
                            reviewFragment.yelpResponse(response);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("error: ", "");

                    }
                });

        queue.add(myReq);

    }




}
