package com.appmate.watchout.util;

import android.Manifest;

public class Constants {
    public static final int GALLERY_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int VIDEO_REQUEST_CODE  = 102;
    public static final int  MAP_BUTTON_REQUEST_CODE = 200;


    public static final int PERMISSION_ALL = 1;
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

}
