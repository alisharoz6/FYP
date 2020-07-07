package com.appmate.watchout.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.appmate.watchout.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;



public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private Timer timer;
    private ImageView iconSplash;

    public static FirebaseAuth mAuth;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (checkIfUserLoggedIn()) {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else {
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

}