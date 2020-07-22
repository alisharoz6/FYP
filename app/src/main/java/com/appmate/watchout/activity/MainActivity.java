package com.appmate.watchout.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.appmate.watchout.R;
import com.appmate.watchout.util.AppUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.appmate.watchout.activity.SplashActivity.logoutUser;
import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.AppUtil.hasPermissions;
import static com.appmate.watchout.util.AppUtil.isLocationEnabled;
import static com.appmate.watchout.util.Constants.LOCATION_PERMISSIONS;
import static com.appmate.watchout.util.Constants.LOCATION_REFRESH_DISTANCE;
import static com.appmate.watchout.util.Constants.LOCATION_REFRESH_TIME;
import static com.appmate.watchout.util.Constants.PERMISSIONS;
import static com.appmate.watchout.util.Constants.PERMISSION_ALL;
import static com.appmate.watchout.util.Constants.currentLocation;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu,btn_location;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;
    private View btnCreateAlert;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private LocationManager mLocationManager;
    LinearLayout btn_create_post;
    private TextView tv_my_location;

    private FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        storage = FirebaseStorage.getInstance("gs://watch-out-7c380.appspot.com");
        // Create a storage reference from our app
        storageRef = storage.getReference();
        requestCurrentLocation();
        setupUI();
        setupTitleBar();
        setupMenu();
        requestCurrentLocation();
    }

    @Override
    protected void onResume() {
        requestCurrentLocation();
        super.onResume();
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
        btn_create_post = findViewById(R.id.btnCreateAlert).findViewById(R.id.btn_create_post);
        btn_create_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestCurrentLocation()){
                    MainActivity.this.startActivity(new Intent(MainActivity.this, PostActivity.class));
                }
            }
        });
        tv_my_location = findViewById(R.id.btnCreateAlert).findViewById(R.id.tv_my_location);
    }

    public void setupTitleBar(){
        btnMenu = findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestCurrentLocation()) {
                    if (menuLayout.getVisibility() == View.VISIBLE) {
                        menuLayout.setVisibility(View.GONE);
                    } else {
                        menuLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btn_location = findViewById(R.id.btn_location);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCurrentLocation();
            }
        });
        activityTitle = findViewById(R.id.tv_bar_title);
        activityTitle.setText("Home");
    }

    public void setupMenu(){
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        if(mAuth!=null){
            tvUsername.setText(mAuth.getCurrentUser().getDisplayName());
            tvEmail.setText(mAuth.getCurrentUser().getEmail());
        }

        btnMenuHome = findViewById(R.id.btnMenuHome);
        btnMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMenu.performClick();
            }
        });
        btnMenuNewsFeed = findViewById(R.id.btnMenuNewsFeed);
        btnMenuNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, NewsFeedActivity.class));
                btnMenu.performClick();
            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                btnMenu.performClick();
            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, HelpContactActivity.class));
                btnMenu.performClick();
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser(mContext);
                MainActivity.this.startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });

    }


    private boolean requestCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!hasPermissions(mContext, LOCATION_PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, LOCATION_PERMISSIONS, PERMISSION_ALL);
        } else {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            if(isLocationEnabled(mContext)){
                Toast.makeText(mContext,"Getting Current Location",Toast.LENGTH_SHORT).show();
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                        LOCATION_REFRESH_DISTANCE, mLocationListener);
                return true;
            }
        }
        return false;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            currentLocation =  new com.appmate.watchout.model.Location(location.getLatitude(),location.getLongitude());
            Log.d("Location Changed", "Lat : "+currentLocation.getLatitude() +"|| Lng :"+currentLocation.getLongitude());
            //Save In Shared Pref
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor =  preferences.edit();
            editor.putString("myLat", String.valueOf(currentLocation.getLatitude()));
            editor.putString("myLng", String.valueOf(currentLocation.getLongitude()));
            editor.commit();
            if(tv_my_location!=null)
            tv_my_location.setText(AppUtil.getCompleteAddressString(mContext,currentLocation.getLatitude(),currentLocation.getLongitude()));
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
    };

    @Override
    protected void onStart() {
        super.onStart();
    }
}
