package com.appmate.watchout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmate.watchout.R;

import static com.appmate.watchout.MyApp.logoutUser;

public class NewsFeedActivity extends AppCompatActivity {

    private String TAG = "NewsFeedActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        setupUI();
        setupTitleBar();
        setupMenu();
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
        activityTitle.setText("News Feed");
    }

    public void setupMenu(){
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvUsername);
        btnMenuHome = findViewById(R.id.btnMenuHome);
        btnMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, MainActivity.class));

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
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, SettingsActivity.class));

            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, HelpContactActivity.class));

            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                logoutUser();
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, SignInActivity.class));
                finish();
            }
        });

    }
}
