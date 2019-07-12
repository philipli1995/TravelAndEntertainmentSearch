package com.philipli.travelandentertainmentsearch.util;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by philipli on 2018/4/5.
 */

public class ImageCache {

    private static List<Bitmap> list = new ArrayList<>();


    public static void setList(List<Bitmap> list1) {
        list.clear();
        list.addAll(list1);
        Log.d("photo", list.size()+"");
    }

    public static List<Bitmap> getList() {

        return list;
    }

}
