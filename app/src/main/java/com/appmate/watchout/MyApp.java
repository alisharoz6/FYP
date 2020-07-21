package com.appmate.watchout;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

public class MyApp extends Application {

//    private static Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
