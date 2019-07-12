package com.philipli.travelandentertainmentsearch.util;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.philipli.travelandentertainmentsearch.TravelEntertainmentSearchApp;
import com.philipli.travelandentertainmentsearch.adapter.FavouriteListAdapter;
import com.philipli.travelandentertainmentsearch.bean.Favourite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Created by philipli on 2018/4/5.
 */

public class ConstantUtil {

    public static final String DDF = "AIzaSyBuyPL93U3YzxYlcHrafYwoIgUu5dYKnWU";
    public static final String FAVOURITE = "FAVOURITE";
    public static final String FAVOURITE_LIST = "FAVOURITE_LIST";
    public static FavouriteListAdapter adapter = null;
    public static TextView empty = null;

    public static void addFavourite(String placeId) {
        List<String> list = getFavourite();
        if (list.contains(placeId)) {
            return;
        }
        ;
        list.add(placeId);
        String cache = list2String(list);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(TravelEntertainmentSearchApp.getInstance());
        SharedPreferences.Editor e = s.edit();
        e.putString(FAVOURITE, cache);
        e.apply();
    }

    public static void removeFavourite(String placeId) {
        List<String> list = getFavourite();
        if (!list.contains(placeId)) {
            return;
        }
        list.remove(placeId);
        String cache = list2String(list);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(TravelEntertainmentSearchApp.getInstance());
        SharedPreferences.Editor e = s.edit();
        e.putString(FAVOURITE, cache);
        e.apply();

    }

    public static List<String> getFavourite() {
        String cache = PreferenceManager.getDefaultSharedPreferences(TravelEntertainmentSearchApp.getInstance()).getString(FAVOURITE, "1,2");
        if (cache.equals("")) {
            return new ArrayList<>();
        }
        return string2List(cache);
    }

    private static String list2String(List<String> list) {
        String ret = "";
        for (String i : list) {
            ret += i + ",";
        }
        return ret.substring(0, ret.length() - 1);
    }

    private static List<String> string2List(String s) {
        String[] m = s.split(",");
        List<String> list = new ArrayList<>();
        for (String e : m) {
            list.add(e);
        }
        return list;
    }


    public static void removeFavouriteList(String placeId) {
        List<Favourite> list = getFavouriteList();
        Favourite cache = null;
        for (Favourite f : list) {
            if (f.getPlaceId().equals(placeId)) {
                cache = f;
            }
        }
        if (cache != null) {
            list.remove(cache);
        }
        save(list);
    }

    public static void addFavouriteList(Favourite favourite) {
        List<Favourite> list = getFavouriteList();
        list.add(favourite);
        save(list);
    }

    public static List<Favourite> getFavouriteList() {
        String cache = PreferenceManager.getDefaultSharedPreferences(TravelEntertainmentSearchApp.getInstance()).getString(FAVOURITE_LIST, "");
        if (cache.equals("")) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<Favourite>>(){}.getType();
        List<Favourite> inpList = new Gson().fromJson(cache, type);
        return inpList;
    }

    private static void save(List<Favourite> list) {
        String ret = new Gson().toJson(list);
        SharedPreferences s = PreferenceManager.getDefaultSharedPreferences(TravelEntertainmentSearchApp.getInstance());
        SharedPreferences.Editor e = s.edit();
        e.putString(FAVOURITE_LIST, ret);
        e.apply();
    }

    public static void updateFavourite() {
        if (adapter == null) {
            return;
        }
        adapter.update();
        adapter.notifyDataSetChanged();
        if (empty != null) {
            if (adapter.getItemCount() == 0) {
                empty.setVisibility(View.VISIBLE);
            }
            else {
                empty.setVisibility(View.INVISIBLE);
            }
        }

    }





}
