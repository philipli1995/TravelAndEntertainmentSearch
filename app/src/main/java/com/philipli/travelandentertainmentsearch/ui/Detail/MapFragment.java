package com.philipli.travelandentertainmentsearch.ui.Detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.philipli.travelandentertainmentsearch.R;
import com.philipli.travelandentertainmentsearch.adapter.CustomAutoCompleteAdapter;
import com.philipli.travelandentertainmentsearch.adapter.MapAutoCompleteAdapter;
import com.philipli.travelandentertainmentsearch.base.RxBaseFragment;
import com.philipli.travelandentertainmentsearch.util.ConstantUtil;
import com.philipli.travelandentertainmentsearch.util.PlaceCache;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by philipli on 2018/4/5.
 */

public class MapFragment extends RxBaseFragment implements OnMapReadyCallback{
    @BindView(R.id.from)
    AutoCompleteTextView mFrom;
    @BindView(R.id.travel_mode)
    Spinner mTravelMode;

    private final static String LAT = "LAT";
    private final static String LNG = "LNG";

    private double lat = 0;
    private double lng = 0;

    private GeoDataClient mClient;

    private MapAutoCompleteAdapter adapter;
    private Place cachePlace = null;

    private GoogleMap mMap;

    @Override
    public int getLayoutId() {
        return R.layout.layout_map_page;
    }

    @Override
    public void finishCreateView(Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = getArguments().getDouble(LAT);
        lng = getArguments().getDouble(LNG);

        adapter = new MapAutoCompleteAdapter(getApplicationContext(), this);
        mFrom.setAdapter(adapter);
        mTravelMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (cachePlace == null) {
                    return;
                }
                requestRoute(cachePlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng location = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(location));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

    }

    public void onAutoCompleteItemSelected(String id) {

        mClient = Places.getGeoDataClient(this.getApplicationContext());
        Task<PlaceBufferResponse> task = mClient.getPlaceById(id);
        task.addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                if (task.isSuccessful()) {
                    PlaceBufferResponse response = task.getResult();
                    Place place = response.get(0).freeze();
                    response.release();
                    mFrom.setText(place.getName());
                    cachePlace = place;
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    requestRoute(place);

                }
                else {
                }
            }
        });

    }

    private String getTravelMode() {
        String ret = "";
        switch (mTravelMode.getSelectedItemPosition()) {
            case 0:
                ret = TransportMode.DRIVING;
                break;
            case 1:
                ret = TransportMode.BICYCLING;
                break;
            case 2:
                ret = TransportMode.TRANSIT;
                break;
            case 3:
                ret = TransportMode.WALKING;
                break;
            default:
                break;
        }
        return ret;
    }

    private void requestRoute(final Place place) {
        GoogleDirection.withServerKey(ConstantUtil.DDF)
                .from(place.getLatLng())
                .to(new LatLng(lat, lng))
                .transportMode(getTravelMode())
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(place.getLatLng()));
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
                            ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                            mMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.BLUE));
//                            setCameraWithCoordinationBounds(route);

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                    }
                });
    }

    public static MapFragment getInstance(double lat, double lng) {
        MapFragment mapFragment = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble(LAT, lat);
        args.putDouble(LNG, lng);
        mapFragment.setArguments(args);
        return mapFragment;
    }
 }
