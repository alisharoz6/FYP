package com.appmate.watchout.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.appmate.watchout.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.opencensus.tags.Tag;

import static com.appmate.watchout.activity.SplashActivity.logoutUser;
import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.AppUtil.hasPermissions;
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
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;
    private View btnCreateAlert;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        storage = FirebaseStorage.getInstance("gs://watch-out-7c380.appspot.com");
        // Create a storage reference from our app
        storageRef = storage.getReference();
        requestCurrentLocation();
        setupUI();
        setupTitleBar();
        setupMenu();
    }

    public void uploadImageVideo(){

        StorageReference imagesRef = storageRef.child("images");
        StorageReference spaceRef = storageRef.child("images/space.jpg");


        // Points to the root reference
        storageRef = storage.getReference();

        // Points to "images"
        imagesRef = storageRef.child("images");

        // Points to "images/space.jpg"
        // Note that you can use variables to create child values
        String fileName = "space.jpg";
        spaceRef = imagesRef.child(fileName);

        // File path is "images/space.jpg"
        String path = spaceRef.getPath();

        // File name is "space.jpg"
        String name = spaceRef.getName();

        // Points to "images"
        imagesRef = spaceRef.getParent();
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
        btnCreateAlert = findViewById(R.id.btnCreateAlert);
        btnCreateAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, PostActivity  .class));
            }
        });
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
        activityTitle.setText("Home");
    }

    public void setupMenu(){
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvUsername);
        tvUsername.setText(mAuth.getCurrentUser().getDisplayName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());

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
            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, HelpContactActivity.class));
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                MainActivity.this.startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

    private void requestCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!hasPermissions(mContext, LOCATION_PERMISSIONS)) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
        } else {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            currentLocation =  new com.appmate.watchout.model.Location(location.getLatitude(),location.getLongitude());
            Log.d("Location Changed", "Lat : "+currentLocation.getLatitude() +"|| Lng :"+currentLocation.getLongitude());
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
