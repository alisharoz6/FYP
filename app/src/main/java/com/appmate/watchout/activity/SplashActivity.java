package com.appmate.watchout.activity;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appmate.watchout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.appmate.watchout.util.AppUtil.isLocationEnabled;
import static com.appmate.watchout.util.Constants.firebaseToken;


public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    public static FirebaseAuth mAuth;
    private String TAG = "SplashActivity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        initFirebaseToken();
        initFirebaseTopic();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkIfUserLoggedIn()) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public boolean checkIfUserLoggedIn(){
        if(mAuth.getCurrentUser() == null){
           return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initFirebaseToken() {
        /*Token Initialization*/
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                    firebaseToken = instanceIdResult.getToken();
                Log.e(TAG + ":Token", firebaseToken);
            }
        });
        /*--------------------*/
    }

    public void initFirebaseTopic() {
        /*Subscribe To Topic*/
        FirebaseMessaging.getInstance().subscribeToTopic("event")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribe Message";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe Message Failed";
                        }
                        Log.d(TAG + ":Topic Subscribe", msg);
                    }
                });
        /*------------------*/
    }

    public static void unSubscribeFirebaseTopic() {
        /*Subscribe To Topic*/
        FirebaseMessaging.getInstance().unsubscribeFromTopic("event")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Un Subscribe Message";
                        if (!task.isSuccessful()) {
                            msg = "Un Subscribe Message Failed";
                        }
                        Log.d("SA" + ":Topic Un Subscribe", msg);
                    }
                });
        /*------------------*/
    }


    public static void logoutUser(Context mCont){
        unSubscribeFirebaseTopic();
        FirebaseAuth.getInstance().signOut();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mCont);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uuid", "");
        editor.commit();
    }


}