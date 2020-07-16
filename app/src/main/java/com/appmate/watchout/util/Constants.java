package com.appmate.watchout.util;

import android.Manifest;

import com.appmate.watchout.model.Location;

public class Constants {
    public static final int GALLERY_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int VIDEO_REQUEST_CODE  = 102;
    public static final int  MAP_BUTTON_REQUEST_CODE = 200;
    public static float LOCATION_REFRESH_DISTANCE = 1;
    public static long LOCATION_REFRESH_TIME = 100;

    public static final  String FCM_TOPIC = "/topics/event";
    public static final  String FCM_API = "https://fcm.googleapis.com/fcm/send";
    public static final  String serverKey = "key=" + "AAAAuGIJPEo:APA91bGUKXakii_mo9i7kdtaXnYftCbdEO_3bDe412flZx1jRG_jbk3-HmicLMN113373qZWCHAMn_8QHxFhGaXkAjb75Xo2cNeKOoKw_y6TRY3gzJbEx5g0u3oeTB2oE8rkeCqHYNkJ";
    public static final  String contentType = "application/json";
    public static  String firebaseToken = "";
    public static Location currentLocation ;
    public static double radiusInMeters = 5.0*1000.0; //1 KM = 1000 Meter


    public static final int PERMISSION_ALL = 1;
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };
    public static final String[] LOCATION_PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };

}
