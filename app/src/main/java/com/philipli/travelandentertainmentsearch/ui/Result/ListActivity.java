package com.philipli.travelandentertainmentsearch.ui.Result;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.PlaceListAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseActivity;
import com.philipli.travelandentertainmentsearch.bean.NearbySearchResponse;
import com.philipli.travelandentertainmentsearch.network.VolleyRequest;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by philipli on 2018/4/3.
 */

public class ListActivity extends RxBaseActivity {

    public static final String RESPONSE = "RESPONSE";
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.previous_button)
    Button mPreviousButton;
    @BindView(R.id.next_button)
    Button mNextButton;
    @BindView(R.id.empty)
    TextView mEmpty;
    private NearbySearchResponse response;
    private PlaceListAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<List<NearbySearchResponse.NearbyPlace>> lists;
    private String nextPageToken;
    private int currentPage = 0;

    private ProgressDialog pd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        response = (NearbySearchResponse) bundle.getSerializable(RESPONSE);

        lists = new ArrayList<>();
        lists.add(response.getResults());
        nextPageToken = response.getNextPageToken();
        if (response.getResults() == null || response.getResults().size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
        }
        processButton();


        adapter = new PlaceListAdapter(getApplicationContext(), response.getResults());
        manager = new LinearLayoutManager(mRecyclerView.getContext());

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.list_title);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    private void processButton() {
        if (currentPage == 0) {
            mPreviousButton.setEnabled(false);
        }
        else {
            mPreviousButton.setEnabled(true);
        }
        if (currentPage == lists.size() - 1 && (nextPageToken == null || nextPageToken.equals(""))) {
            mNextButton.setEnabled(false);
        }
        else {
            mNextButton.setEnabled(true);
        }
    }

    public static void start(Context context, NearbySearchResponse response) {
        Intent intent = new Intent(context, ListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESPONSE, response);
        intent.putExtras(bundle);
        ActivityCompat.startActivity(context, intent, null);
    }

    private void previousPage() {
        if (currentPage == 0) {
            return;
        }
        currentPage--;
        adapter.update(lists.get(currentPage));
        adapter.notifyDataSetChanged();
        processButton();
        return;
    }

    private void nextPage() {

        if (currentPage < lists.size() - 1) {
            currentPage++;
            adapter.update(lists.get(currentPage));
            adapter.notifyDataSetChanged();
            processButton();
            return;
        }

        if (nextPageToken == null || nextPageToken.equals("")) {
            return;
        }

        pd = ProgressDialog.show(this, "", "Fetching results");
        String url = VolleyRequest.BACKEND_URL;
        url += "nextToken=" + nextPageToken;
        VolleyRequest.nextPageService(url, this);

    }

    public void onNextPage(NearbySearchResponse response) {
        if (response.getNextPageToken() == null || response.getNextPageToken().equals("")) {
            nextPageToken = "";
        }
        lists.add(response.getResults());
        adapter.update(response.getResults());
        adapter.notifyDataSetChanged();
        currentPage++;
        processButton();
        pd.dismiss();
    }

    public void onErrorPage() {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }


    @OnClick({R.id.previous_button, R.id.next_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.previous_button:
                previousPage();
                break;
            case R.id.next_button:
                nextPage();
                break;
        }
    }
}
