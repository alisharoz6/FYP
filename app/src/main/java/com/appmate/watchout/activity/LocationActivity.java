package com.appmate.watchout.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.appmate.watchout.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.maps.GeoApiContext;
import com.schibstedspain.leku.LekuPoi;
import com.schibstedspain.leku.LocationPickerActivity;
import com.schibstedspain.leku.geocoder.GeocoderPresenter;
import com.schibstedspain.leku.geocoder.GeocoderRepository;
import com.schibstedspain.leku.geocoder.GeocoderViewInterface;
import com.schibstedspain.leku.geocoder.GoogleGeocoderDataSource;
import com.schibstedspain.leku.geocoder.places.GooglePlacesDataSource;
import com.schibstedspain.leku.geocoder.timezone.GoogleTimeZoneDataSource;
import com.schibstedspain.leku.locale.SearchZoneRect;
import com.schibstedspain.leku.utils.ReactiveLocationProvider;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import kotlin.Pair;

import static com.appmate.watchout.MyApp.logoutUser;
import static com.schibstedspain.leku.LocationPickerActivityKt.ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LATITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.LEKU_POI;
import static com.schibstedspain.leku.LocationPickerActivityKt.LOCATION_ADDRESS;
import static com.schibstedspain.leku.LocationPickerActivityKt.LONGITUDE;
import static com.schibstedspain.leku.LocationPickerActivityKt.TIME_ZONE_DISPLAY_NAME;
import static com.schibstedspain.leku.LocationPickerActivityKt.TIME_ZONE_ID;
import static com.schibstedspain.leku.LocationPickerActivityKt.TRANSITION_BUNDLE;
import static com.schibstedspain.leku.LocationPickerActivityKt.ZIPCODE;

public class LocationActivity extends AppCompatActivity implements LocationListener, GeocoderViewInterface {

    private String TAG = "LocationActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;

    private Button map_button;
    private final int  MAP_BUTTON_REQUEST_CODE = 1;
    private final int MAP_POIS_BUTTON_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
        mContext = this;
        setupUI();
        setupTitleBar();
        setupMenu();
        startLocation();
//        SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197))
        map_button =    findViewById(R.id.map_button);
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationPickerIntent =   new LocationPickerActivity.Builder()
                        .withLocation(31.5204, 74.3587)
                        .withGeolocApiKey("AIzaSyDgIjrCXSxiH31ghL2fffio6Os7Y1X2JXQ")
                        .withSearchZone("en_PK")
//                        .withSearchZone(new SearchZoneRect(LatLng(26.525467, -18.910366), LatLng(43.906271, 5.394197)))
                        .withDefaultLocaleSearchZone()
                        .shouldReturnOkOnBackPressed()
//                        .withStreetHidden()
//                        .withCityHidden()
//                        .withZipCodeHidden()
//                        .withSatelliteViewHidden()
//                        .withGooglePlacesEnabled()
                        .withGoogleTimeZoneEnabled()
//                        .withVoiceSearchHidden()
//                        .withUnnamedRoadHidden()
                        .build(mContext);

                // this is optional if you want to return RESULT_OK if you don't set the latitude/longitude and click back button
//                locationPickerIntent.putExtra("test", "this is a test");

                startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);
            }
        });


    }

    public void startLocation(){
//             Intent locationPickerIntent =   new LocationPickerActivity.Builder()
//                .withLocation(41.4036299, 2.1743558)
//                .withGeolocApiKey("AIzaSyDgIjrCXSxiH31ghL2fffio6Os7Y1X2JXQ")
//                .withSearchZone("es_ES")
//                .withSearchZone()
//                .withDefaultLocaleSearchZone()
//                .shouldReturnOkOnBackPressed()
//                .withStreetHidden()
//                .withCityHidden()
//                .withZipCodeHidden()
//                .withSatelliteViewHidden()
//                .withGooglePlacesEnabled()
//                .withGoogleTimeZoneEnabled()
//                .withVoiceSearchHidden()
//                .withUnnamedRoadHidden()
//                .build(mContext);
//
//        startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Log.d("RESULT****", "OK");
            if (requestCode == 1) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                Log.d("LATITUDE****", String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                Log.d("LONGITUDE****", String.valueOf(longitude));
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.d("ADDRESS****", address.toString());
                String postalcode = data.getStringExtra(ZIPCODE);
                Log.d("POSTALCODE****", postalcode.toString());
//                Bundle bundle = data.getBundleExtra(TRANSITION_BUNDLE);
//                Log.d("BUNDLE TEXT****", bundle.getString("test"));
    //                Bundle fullAddress =  data.getParcelableExtra(ADDRESS);
    //                if (fullAddress != null) {
    //                    Log.d("FULL ADDRESS****", fullAddress.toString());
    //                }
//                String timeZoneId = data.getStringExtra(TIME_ZONE_ID);
//                Log.d("TIME ZONE ID****", timeZoneId);
//                String timeZoneDisplayName = data.getStringExtra(TIME_ZONE_DISPLAY_NAME);
//                Log.d("TIME ZONE NAME****", timeZoneDisplayName);
            } else if (requestCode == 2) {
                double latitude = data.getDoubleExtra(LATITUDE, 0.0);
                Log.d("LATITUDE****", String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LONGITUDE, 0.0);
                Log.d("LONGITUDE****", String.valueOf(longitude));
                String address = data.getStringExtra(LOCATION_ADDRESS);
                Log.d("ADDRESS****", address.toString());
                Bundle lekuPoi = data.getParcelableExtra(LEKU_POI);
                Log.d("LekuPoi****", lekuPoi.toString());
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("RESULT****", "CANCELLED");
        }
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
    }

    public void setupTitleBar(){
        btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuLayout.getVisibility()==View.VISIBLE){
                    menuLayout.setVisibility(View.GONE);
                }
                else{
                    menuLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        activityTitle = findViewById(R.id.tv_bar_title);
        activityTitle.setText("Choose Location");
    }

    public void setupMenu(){
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvUsername);
        btnMenuHome = findViewById(R.id.btnMenuHome);
        btnMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                LocationActivity.this.startActivity(new Intent(LocationActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMenuNewsFeed = findViewById(R.id.btnMenuNewsFeed);
        btnMenuNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                btnMenu.performClick();
            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                LocationActivity.this.startActivity(new Intent(LocationActivity.this, SettingsActivity.class));
                finish();
            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                LocationActivity.this.startActivity(new Intent(LocationActivity.this, HelpContactActivity.class));
                finish();
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                logoutUser();
                LocationActivity.this.startActivity(new Intent(LocationActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void didGetLastLocation() {

    }

    @Override
    public void didGetLocationInfo() {

    }

    @Override
    public void didLoadLocation() {

    }

    @Override
    public void showDebouncedLocations(@NotNull List<? extends Address> list) {

    }

    @Override
    public void showGetLocationInfoError() {

    }

    @Override
    public void showLastLocation(@NotNull Location location) {

    }

    @Override
    public void showLoadLocationError() {

    }

    @Override
    public void showLocationInfo(@NotNull Pair<? extends Address, ? extends TimeZone> pair) {

    }

    @Override
    public void showLocations(@NotNull List<? extends Address> list) {

    }

    @Override
    public void willGetLocationInfo(@NotNull LatLng latLng) {

    }

    @Override
    public void willLoadLocation() {

    }
}
