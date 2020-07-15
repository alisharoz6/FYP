package com.appmate.watchout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmate.watchout.R;
import com.appmate.watchout.adapter.NewsFeedAdapter;
import com.appmate.watchout.model.Data;
import com.appmate.watchout.model.Location;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import static com.appmate.watchout.MyApp.logoutUser;

public class NewsFeedActivity extends AppCompatActivity {

    private String TAG = "NewsFeedActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;

    private Button btn_data;

    private RecyclerView rvNewsFeed;
    private ArrayList<Data> feeds = new ArrayList<>();

    /*Firebase*/
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        mContext = this;
        setupUI();
        setupTitleBar();
        setupMenu();
        btn_data = findViewById(R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFB();
            }
        });
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);

        rvNewsFeed = findViewById(R.id.rv_feed);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(mContext));
        rvNewsFeed.setItemAnimator(new DefaultItemAnimator());
        rvNewsFeed.setAdapter(new NewsFeedAdapter(mContext,feeds));
        
        loadDataFromFB();
    }

    private void loadDataFromFB() {
        db = FirebaseFirestore.getInstance();
        loadData();
    }

    private void loadData() {
        db.collection("events").document("post")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<HashMap<String , Data>> data = (ArrayList<HashMap<String , Data>>) documentSnapshot.get("posts");
                        mapDataToModel(data);
                        Toast.makeText(NewsFeedActivity.this, "Data Loaded!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewsFeedActivity.this, "Data Load Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void mapDataToModel(ArrayList<HashMap<String, Data>> data) {
        for(HashMap map : data){
            Location location = null;
            if(map.get("location") != null){
                HashMap locationMap = (HashMap) map.get("location");
                location =  new Location( (double) locationMap.get("latitude") , (double) locationMap.get("longitude"));
            }
            feeds.add(new Data((String) map.get("id"),(String) map.get("userName"), (String) map.get("userId"), (String)  map.get("userEmail") ,(String)  map.get("eventName"), (String) map.get("video") , (String)  map.get("image"),location ,(String)   map.get("serverity")
                    , (String) map.get("type") , (long) map.get("alertCount") , (long) map.get("reportCount")));
        }
        rvNewsFeed.getAdapter().notifyDataSetChanged();
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
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, SettingsActivity.class));
                finish();
            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, HelpContactActivity.class));
                finish();
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
