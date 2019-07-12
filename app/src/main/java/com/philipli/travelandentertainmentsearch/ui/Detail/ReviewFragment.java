package com.philipli.travelandentertainmentsearch.ui.Detail;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.ReviewAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;
import com.philipli.travelandentertainmentsearch.bean.DetailResponse;
import com.philipli.travelandentertainmentsearch.bean.YelpReviewResponse;
import com.philipli.travelandentertainmentsearch.network.VolleyRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

import butterknife.BindView;

/**
 * Created by philipli on 2018/4/6.
 */

public class ReviewFragment extends RxBaseFragment {
    @BindView(R.id.review_type)
    Spinner reviewType;
    @BindView(R.id.review_order)
    Spinner reviewOrder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    TextView empty;

    public static final String RESPONSE = "RESPONSE";

    private ReviewAdapter adapter;
    private DetailResponse response;
    private boolean yelpAttempt = false;


    @Override
    public int getLayoutId() {
        return R.layout.layout_reviews_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {
        Bundle args = getArguments();
        response = (DetailResponse) args.getSerializable(RESPONSE);
        if (response.getResult().getReviews() == null || response.getResult().getReviews().size() == 0) {
            empty.setVisibility(View.VISIBLE);
        }
        List<DetailResponse.DetailResult.Review> review = new ArrayList<>();;
        if (response.getResult().getReviews() != null) {
            review = response.getResult().getReviews();
        }
        adapter = new ReviewAdapter(getApplicationContext(), review, empty);
        LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        reviewOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.onOrderSelected(reviewOrder.getSelectedItemPosition());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        reviewType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (reviewType.getSelectedItemPosition() == 1 && !yelpAttempt) {
                    yelpAttempt = true;
                    yelpRequest();
                }
                else {
                    adapter.onTypeSelected(reviewType.getSelectedItemPosition());
                    adapter.onOrderSelected(reviewOrder.getSelectedItemPosition());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void yelpRequest() {
        String url = VolleyRequest.BACKEND_URL;
        String city = "";
        String state = "";
        String country = "";
        for (DetailResponse.DetailResult.AddressComponent c : response.getResult().getAddress_components()) {
            for (String type : c.getTypes()) {
                if (type.equals("locality")) {
                    city = c.getShort_name();
                }
                else if (type.equals("administrative_area_level_1")) {
                    state = c.getShort_name();
                }
                else if (type.equals("country")) {
                    country = c.getShort_name();
                }
            }
        }
        String address = response.getResult().getFormatted_address();
        address = address.substring(0, address.indexOf(","));
        url += "name=" + response.getResult().getName();
        url += "&city=" + city;
        url += "&state=" + state;
        url += "&country=" + country;
        url += "&address=" + address;
        VolleyRequest.yelpService(url, this);

    }

    private List<DetailResponse.DetailResult.Review> yelpReviewStandardize(List<YelpReviewResponse.YelpReivew> list) throws ParseException {


        List<DetailResponse.DetailResult.Review> ret = new ArrayList<>();
        if (list == null) {
            return ret;
        }
        for (YelpReviewResponse.YelpReivew review : list) {
            String formattedTime = review.getTime_created();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            long time = sdf.parse(formattedTime).getTime() / 1000;
            ret.add(new DetailResponse.DetailResult.Review(review.getUser().getName(),
                    review.getUrl(), review.getRating(), time, review.getText(),
                    review.getUser().getImage_url()));
        }
        return ret;
    }

    public void yelpResponse(YelpReviewResponse response) throws ParseException {
        adapter.addYelpReview(yelpReviewStandardize(response.getReviews()));
        adapter.onTypeSelected(reviewType.getSelectedItemPosition());
        adapter.onOrderSelected(reviewOrder.getSelectedItemPosition());
        adapter.notifyDataSetChanged();
    }

    public static ReviewFragment getInstance(DetailResponse response) {
        Bundle args = new Bundle();
        args.putSerializable(RESPONSE, response);
        ReviewFragment reviewFragment = new ReviewFragment();
        reviewFragment.setArguments(args);
        return reviewFragment;
    }

}
