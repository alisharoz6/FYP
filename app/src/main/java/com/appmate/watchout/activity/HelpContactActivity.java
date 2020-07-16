package com.appmate.watchout.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appmate.watchout.R;
import com.appmate.watchout.singleton.MySingleton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.appmate.watchout.activity.SplashActivity.logoutUser;
import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.Constants.FCM_API;
import static com.appmate.watchout.util.Constants.FCM_TOPIC;
import static com.appmate.watchout.util.Constants.contentType;
import static com.appmate.watchout.util.Constants.serverKey;

public class HelpContactActivity extends AppCompatActivity {

    private String TAG = "HelpContactActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;
    private TextView tvContactEmail,tvAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_contact);
        mContext = this;
        setupUI();
        setupTitleBar();
        setupMenu();

    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
        tvContactEmail = findViewById(R.id.tv_contact_email);
        tvAboutUs = findViewById(R.id.tv_about);

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
        activityTitle.setText("Help/About Us");
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
                HelpContactActivity.this.startActivity(new Intent(HelpContactActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMenuNewsFeed = findViewById(R.id.btnMenuNewsFeed);
        btnMenuNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpContactActivity.this.startActivity(new Intent(HelpContactActivity.this, NewsFeedActivity.class));
                finish();

            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpContactActivity.this.startActivity(new Intent(HelpContactActivity.this, SettingsActivity.class));
                finish();

            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMenu.performClick();
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                HelpContactActivity.this.startActivity(new Intent(HelpContactActivity.this, SignInActivity.class));
                finish();
            }
        });

    }
}
