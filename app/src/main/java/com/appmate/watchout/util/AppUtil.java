package com.appmate.watchout.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.appmate.watchout.util.Constants.radiusInMeters;


public class AppUtil {

    public static boolean validateSignInForm(String email, String password, Context context) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "Email Address Invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean validateSignUpForm(String name,String email, String password,String confirmPassword, Context context) {

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, "Enter Name ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(context, "Email Address Invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(context, "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(context, "Enter Confirm password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(context, "Password Not Matched!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean validateChangePasswordForm(String currentPassword, String newPassword,String newConfirmPassword, Context context) {

        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(context, "Enter current password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(context, "Enter new password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(newConfirmPassword)) {
            Toast.makeText(context, "Enter  confirm password!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!newPassword.equalsIgnoreCase(newConfirmPassword)) {
            Toast.makeText(context, "Password Not Matched!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean validateChangeProfileForm(String name, String mobile,Context context) {

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(context, "Enter Full Name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(context, "Enter mobile number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean checkLocationWithInRadius(com.appmate.watchout.model.Location oldPosition, com.appmate.watchout.model.Location currentPosition){
        float[] distance = new float[1];
        Location.distanceBetween(oldPosition.getLatitude(), oldPosition.getLongitude(),
                currentPosition.getLatitude(), currentPosition.getLongitude(), distance);
        if(distance[0] > radiusInMeters ){
            return false;
        } else {
            return true;
        }
    }
}
