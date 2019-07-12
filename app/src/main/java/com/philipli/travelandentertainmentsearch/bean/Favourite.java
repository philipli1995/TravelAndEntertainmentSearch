package com.philipli.travelandentertainmentsearch.bean;

import java.io.Serializable;

/**
 * Created by philipli on 2018/4/6.
 */

public class Favourite implements Serializable {

    private String placeId;
    private String title;
    private String vicinity;
    private String iconUrl;

    public Favourite(String placeId, String title, String vicinity, String iconUrl) {
        this.placeId = placeId;
        this.title = title;
        this.vicinity = vicinity;
        this.iconUrl = iconUrl;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getTitle() {
        return title;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "placeId='" + placeId + '\'' +
                ", title='" + title + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
