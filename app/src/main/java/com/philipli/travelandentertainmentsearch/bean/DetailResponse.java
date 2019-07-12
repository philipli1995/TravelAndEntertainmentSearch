package com.philipli.travelandentertainmentsearch.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by philipli on 2018/4/5.
 */

public class DetailResponse implements Serializable {


    DetailResult result;

    public DetailResult getResult() {
        return result;
    }

    public static class DetailResult implements Serializable {
        private List<AddressComponent> address_components;


        private String url;
        private List<Review> reviews;
        private String name;
        private String formatted_address;

        public String getName() {
            return name;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public String getUrl() {
            return url;
        }

        public List<AddressComponent> getAddress_components() {
            return address_components;
        }


        public List<Review> getReviews() {
            return reviews;
        }

        public class AddressComponent implements Serializable {
            String long_name;
            String short_name;
            List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public List<String> getTypes() {
                return types;
            }
        }

        public static class Review implements Serializable {
            private String author_name;
            private String author_url;
            private float rating;
            private long time;
            private String text;
            private String profile_photo_url;

            public Review(String author_name, String author_url, float rating, long time, String text, String profile_photo_url) {
                this.author_name = author_name;
                this.author_url = author_url;
                this.rating = rating;
                this.time = time;
                this.text = text;
                this.profile_photo_url = profile_photo_url;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public String getAuthor_url() {
                return author_url;
            }

            public float getRating() {
                return rating;
            }

            public long getTime() {
                return time;
            }

            public String getText() {
                return text;
            }

            public String getProfile_photo_url() {
                return profile_photo_url;
            }
        }
    }


 }
