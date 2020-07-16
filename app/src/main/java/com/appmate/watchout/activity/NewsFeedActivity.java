package com.appmate.watchout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appmate.watchout.R;
import com.appmate.watchout.adapter.NewsFeedAdapter;
import com.appmate.watchout.model.Data;
import com.appmate.watchout.model.Location;
import com.appmate.watchout.singleton.MySingleton;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.appmate.watchout.activity.SplashActivity.logoutUser;
import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.Constants.FCM_API;
import static com.appmate.watchout.util.Constants.FCM_TOPIC;
import static com.appmate.watchout.util.Constants.contentType;
import static com.appmate.watchout.util.Constants.serverKey;

public class NewsFeedActivity extends AppCompatActivity {

    private String TAG = "NewsFeedActivity";
    private Context mContext;

    private View menuLayout,loadingLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;

    private Button btn_data,btn_data2;

    private SpinKitView progressBar;

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
        setupProgressBar();
        btn_data = findViewById(R.id.btn_data);
        btn_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(0, "alert");
            }
        });
        btn_data2 = findViewById(R.id.btn_data2);
        btn_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(0, "report");
            }
        });
    }

    private void setupProgressBar() {
        progressBar =  findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    public void showProgress(){
        loadingLayout.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
        loadingLayout.setVisibility(View.GONE);
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
        loadingLayout = findViewById(R.id.loadingLayout);

        rvNewsFeed = findViewById(R.id.rv_feed);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(mContext));
        rvNewsFeed.setItemAnimator(new DefaultItemAnimator());
        rvNewsFeed.setAdapter(new NewsFeedAdapter(mContext,feeds));
        
        loadDataFromFB();
    }

    public void loadDataFromFB() {
        db = FirebaseFirestore.getInstance();
        loadData();
    }

    public void loadData() {
        showProgress();
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
                        hideProgress();
                    }
                });
    }

    public void deleteEvent(HashMap map, int index){
        showProgress();
        db.collection("events").document("post").update("posts", FieldValue.arrayRemove(map)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hideProgress();
                Toast.makeText(NewsFeedActivity.this, "Incident Deleted Successfully!",
                        Toast.LENGTH_SHORT).show();
                feeds.remove(index);
                rvNewsFeed.getAdapter().notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewsFeedActivity.this, "Updated Failed",
                        Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    public void updateAlertReportCount(ArrayList<HashMap<String, Data>> data, HashMap mynewMap, int index, String clickType){
        long reportCount = 0;
        long alertCount = 0;
        if(clickType.equalsIgnoreCase("alert")){
            alertCount = (long) mynewMap.get("alertCount");
            alertCount++;
            mynewMap.put("alertCount", alertCount);
        }
        else{
            reportCount = (long) mynewMap.get("reportCount");
            reportCount++;
            mynewMap.put("reportCount", reportCount);
        }
        data.set(index, mynewMap);
        long finalReportCount = reportCount;
        long finalAlertCount = alertCount;
        db.collection("events").document("post")
                .update("posts", data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                if(clickType.equalsIgnoreCase("alert"))
                    Toast.makeText(NewsFeedActivity.this, "Alert Marked!",  Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NewsFeedActivity.this, "Report Marked!",  Toast.LENGTH_SHORT).show();
                if(finalReportCount >5){
                    deleteEvent(mynewMap,index);
                    Toast.makeText(NewsFeedActivity.this, "Event Deleted!",
                            Toast.LENGTH_SHORT).show();
                }
                else if(finalAlertCount >5){
                    //Trigger Firebase Message Event
                    Data data = getDataFromMap(mynewMap);
                    if(data!=null){
                        createNotification(data);
                        Toast.makeText(NewsFeedActivity.this, "Alert Generated!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(NewsFeedActivity.this, "Alert Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                hideProgress();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewsFeedActivity.this, "Update Data Failed!",
                        Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }
    public void updateData(int index, String clickType) {
        showProgress();
        db.collection("events").document("post")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        ArrayList<HashMap<String, Data>> data = (ArrayList<HashMap<String, Data>>) documentSnapshot.get("posts");
                        HashMap mynewMap = data.get(index);
                        updateAlertReportCount(data,mynewMap,index,clickType);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewsFeedActivity.this, "Data Load Failed!",
                        Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    public void mapDataToModel(ArrayList<HashMap<String, Data>> data) {
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
        hideProgress();
    }

    public Data getDataFromMap(HashMap map) {
            Location location = null;
            if(map.get("location") != null){
                HashMap locationMap = (HashMap) map.get("location");
                location =  new Location( (double) locationMap.get("latitude") , (double) locationMap.get("longitude"));
            }
            return  new Data((String) map.get("id"),(String) map.get("userName"), (String) map.get("userId"), (String)  map.get("userEmail") ,(String)  map.get("eventName"), (String) map.get("video") , (String)  map.get("image"),location ,(String)   map.get("serverity")
                    , (String) map.get("type") , (long) map.get("alertCount") , (long) map.get("reportCount"));
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
        tvUsername.setText(mAuth.getCurrentUser().getDisplayName());
        tvEmail.setText(mAuth.getCurrentUser().getEmail());
        btnMenuHome = findViewById(R.id.btnMenuHome);
        btnMenuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMenuNewsFeed = findViewById(R.id.btnMenuNewsFeed);
        btnMenuNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMenu.performClick();
                loadDataFromFB();
            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, SettingsActivity.class));
                finish();
            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, HelpContactActivity.class));
                finish();
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
                NewsFeedActivity.this.startActivity(new Intent(NewsFeedActivity.this, SignInActivity.class));
                finish();
            }
        });
    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NewsFeedActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void createNotification(Data data){
//        TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            if( data.getType()!=null) notifcationBody.put("title", data.getType());
            if( data.getEvent()!=null) notifcationBody.put("message", data.getEvent());
            if( mAuth.getCurrentUser()!=null) notifcationBody.put("createdBy" , mAuth.getCurrentUser().getUid());
            if( data.getLocation()!=null) notifcationBody.put("lat",data.getLocation().getLatitude());
            if( data.getLocation()!=null) notifcationBody.put("lng",data.getLocation().getLongitude());
            notification.put("to", FCM_TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);
    }
}
