package com.philipli.travelandentertainmentsearch.ui.Home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.Visibility;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.TravelEntertainmentSearchApp;
import com.philipli.travelandentertainmentsearch.adapter.CustomAutoCompleteAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;
import com.philipli.travelandentertainmentsearch.network.VolleyRequest;

import com.google.android.gms.location.LocationServices;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by philipli on 2018/4/2.
 */

public class SearchFragment extends RxBaseFragment {


    @BindView(R.id.key_error)
    TextView mKeyError;
    @BindView(R.id.keyword)
    EditText mKeyword;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.distance)
    EditText mDistance;
    @BindView(R.id.currentRadioButton)
    RadioButton mCurrentRadioButton;
    @BindView(R.id.otherRadioButton)
    RadioButton mOtherRadioButton;
    @BindView(R.id.other_error)
    TextView mOtherError;
    @BindView(R.id.other)
    AutoCompleteTextView mOther;
    @BindView(R.id.search_button)
    Button mSearchButton;
    @BindView(R.id.clear_button)
    Button mClearButton;

    ProgressDialog pd;
    private boolean mLocationPermissionGranted;
    private Location currentLocation;

    private String[] categoryValues;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleApiClient mGoogleApiClient;
    LocationManager mLocationManager;


    @Override
    public int getLayoutId() {
        return R.layout.layout_search_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {

        categoryValues = TravelEntertainmentSearchApp.getInstance().getResources().getStringArray(R.array.category_value);


        CustomAutoCompleteAdapter adapter = new CustomAutoCompleteAdapter(getApplicationContext());
        mOther.setAdapter(adapter);

        getLocationPermission();

        if (mLocationPermissionGranted) {
            currentLocation = getDeviceLocation();
        }
        Log.d("lat:", "" + currentLocation.getLatitude());
        Log.d("lon:", "" + currentLocation.getLongitude());


        mKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().equals("")) {
                    mKeyError.setVisibility(View.VISIBLE);
                } else {
                    mKeyError.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mOther.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mOtherRadioButton.isChecked() && charSequence.toString().trim().equals("")) {
                    mOtherError.setVisibility(View.VISIBLE);
                } else {
                    mOtherError.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static SearchFragment getInstance() {
        SearchFragment searchFragment = new SearchFragment();
        return searchFragment;
    }


    @OnClick({R.id.search_button, R.id.clear_button, R.id.currentRadioButton, R.id.otherRadioButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_button:
                login();
                break;
            case R.id.clear_button:
                clear();
                break;
            case R.id.currentRadioButton:
                mOther.setText("");
                mOtherError.setVisibility(View.GONE);
                mOtherRadioButton.setChecked(false);
                break;
            case R.id.otherRadioButton:
                mCurrentRadioButton.setChecked(false);
                break;
        }
    }

    private void clear() {
        mDistance.setText("");
        mSpinner.setSelection(0);
        mCurrentRadioButton.setChecked(false);
        mKeyError.setVisibility(View.GONE);
        mKeyword.setText("");
        mOther.setText("");
        mOtherError.setVisibility(View.GONE);
        mOtherRadioButton.setChecked(false);
    }

    private void login() {
        boolean other = mOtherRadioButton.isChecked() && mOther.getText().toString().trim().equals("");
        boolean key = mKeyword.getText().toString().trim().equals("");
        boolean none = !mOtherRadioButton.isChecked() && !mCurrentRadioButton.isChecked();
        if (other || key || none) {
            Toast.makeText(getApplicationContext(), "Please fix all fields with errors", Toast.LENGTH_SHORT).show();
            return;
        }

        String dis = mDistance.getText().toString();
        if (dis.equals("")) {
            dis = "10";
        }

        String url = VolleyRequest.BACKEND_URL;
        url += "keyWord=" + mKeyword.getText().toString();
        url += "&type=" + categoryValues[mSpinner.getSelectedItemPosition()];
        url += "&distance=" + dis;
        url += "&here=" + mOther.getText().toString();
        url += "&lat=" + currentLocation.getLatitude();
        url += "&lon=" + currentLocation.getLongitude();
//        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
        pd = ProgressDialog.show(getContext(), "", "Fetching results");
        VolleyRequest.nearbySearchService(url, this.getContext(), this);
    }

    public void onFinishSearch() {
        pd.dismiss();
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getSupportActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @SuppressLint("MissingPermission")
    private Location getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }

        }
        if (bestLocation == null) {
            bestLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return bestLocation;


    }
}
