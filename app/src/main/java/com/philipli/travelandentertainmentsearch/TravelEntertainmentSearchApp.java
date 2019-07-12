package com.philipli.travelandentertainmentsearch;

import android.app.Application;

/**
 * Created by philipli on 2018/4/1.
 */

public class TravelEntertainmentSearchApp  extends Application{


    public static TravelEntertainmentSearchApp mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static TravelEntertainmentSearchApp getInstance() {
        return mInstance;
    }



}
