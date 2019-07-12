package com.philipli.travelandentertainmentsearch.util;

import com.philipli.travelandentertainmentsearch.bean.DetailResponse;

import java.util.Comparator;

/**
 * Created by philipli on 2018/4/6.
 */

public class ReviewComparator {



    public static Comparator<DetailResponse.DetailResult.Review> highRating = new Comparator<DetailResponse.DetailResult.Review>() {
        @Override
        public int compare(DetailResponse.DetailResult.Review r1, DetailResponse.DetailResult.Review r2) {
            return (int)(r2.getRating()-r1.getRating());
        }
    };

    public static Comparator<DetailResponse.DetailResult.Review> lowRating = new Comparator<DetailResponse.DetailResult.Review>() {
        @Override
        public int compare(DetailResponse.DetailResult.Review r1, DetailResponse.DetailResult.Review r2) {
            return (int)(r1.getRating()-r2.getRating());
        }
    };

    public static Comparator<DetailResponse.DetailResult.Review> highTime = new Comparator<DetailResponse.DetailResult.Review>() {
        @Override
        public int compare(DetailResponse.DetailResult.Review r1, DetailResponse.DetailResult.Review r2) {
            return (int)(r2.getTime() - r1.getTime());
        }
    };

    public static Comparator<DetailResponse.DetailResult.Review> lowTime = new Comparator<DetailResponse.DetailResult.Review>() {
        @Override
        public int compare(DetailResponse.DetailResult.Review r1, DetailResponse.DetailResult.Review r2) {
            return (int)(r1.getTime() - r2.getTime());
        }
    };

    public static Comparator<DetailResponse.DetailResult.Review>[] comparators = new Comparator[]{highRating, lowRating, highTime, lowTime};
}
