package com.philipli.travelandentertainmentsearch.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by philipli on 2018/4/3.
 */

public class NearbySearchResponse implements Serializable{

    @SerializedName("next_page_token")
    private String nextPageToken;
    private List<NearbyPlace> results;

    public class NearbyPlace implements Serializable{
        private Geolocation gemometry;
        private String icon;
        private String id;
        private String name;
        private String place_id;
        private String price_level;
        private String rating;
        private String reference;

        @Override
        public String toString() {
            return "NearbyPlace{" +
                    "gemometry=" + gemometry +
                    ", icon='" + icon + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", place_id='" + place_id + '\'' +
                    ", price_level='" + price_level + '\'' +
                    ", rating='" + rating + '\'' +
                    ", reference='" + reference + '\'' +
                    ", scope='" + scope + '\'' +
                    ", types=" + types +
                    ", vicinity='" + vicinity + '\'' +
                    '}';
        }

        public Geolocation getGemometry() {
            return gemometry;
        }

        public String getIcon() {
            return icon;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public String getPrice_level() {
            return price_level;
        }

        public String getRating() {
            return rating;
        }

        public String getReference() {
            return reference;
        }

        public String getScope() {
            return scope;
        }

        public List<String> getTypes() {
            return types;
        }

        public String getVicinity() {
            return vicinity;
        }

        private String scope;
        private List<String> types;
        private String vicinity;




    }

    public class Geolocation implements Serializable{

        private Location location;

        public Location getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return "Geolocation{" +
                    "location=" + location +
                    '}';
        }
    }

    public class Location implements Serializable{

        private String lat;
        private String lng;

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }

//    public class Photo {
//        private String height;
//        private String width;
//        private List<String> html_attributions;
//        private String photo_reference;
//
//    }


    public String getNextPageToken() {
        return nextPageToken;
    }

    public List<NearbyPlace> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "NearbySearchResponse{" +
                "nextPageToken='" + nextPageToken + '\'' +
                ", results=" + results +
                '}';
    }
}
