package com.philipli.travelandentertainmentsearch.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by philipli on 2018/4/6.
 */

public class YelpReviewResponse implements Serializable {

    List<YelpReivew> reviews;

    public List<YelpReivew> getReviews() {
        return reviews;
    }

    public class YelpReivew implements Serializable{
        String url;
        String text;
        float rating;
        String time_created;
        YelpUser user;



        public String getUrl() {
            return url;
        }

        public String getText() {
            return text;
        }

        public float getRating() {
            return rating;
        }

        public String getTime_created() {
            return time_created;
        }

        public YelpUser getUser() {
            return user;
        }
    }

    public class YelpUser implements Serializable {
        String image_url;
        String name;

        public String getImage_url() {
            return image_url;
        }

        public String getName() {
            return name;
        }
    }
 }
