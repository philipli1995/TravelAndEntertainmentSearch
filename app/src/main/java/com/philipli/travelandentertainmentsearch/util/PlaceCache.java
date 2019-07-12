package com.philipli.travelandentertainmentsearch.util;

import com.google.android.gms.location.places.Place;

/**
 * Created by philipli on 2018/4/5.
 */

public class PlaceCache {
    private static Place placeCache = null;
    public static Place getPlace() {
        return placeCache;
    }
    public static void setPlace(Place place) {
        placeCache = place;
    }
}
