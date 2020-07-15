package com.appmate.watchout.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmate.watchout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.appmate.watchout.MyApp.logoutUser;
import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.AppUtil.validateChangePasswordForm;
import static com.appmate.watchout.util.AppUtil.validateChangeProfileForm;

public class SettingsActivity extends AppCompatActivity {

    private String TAG = "SettingsActivity";
    private Context mContext;

    private View menuLayout;
    private ImageView btnMenu;
    private TextView activityTitle;
    private TextView tvUsername,tvEmail,btnMenuHome,btnMenuNewsFeed,btnMenuSettings,btnMenuHelpAboutUs,btnMenuLogout;
    private Button btn_edit_profile,btn_change_password,btn_contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = this;
        setupUI();
        setupTitleBar();
        setupMenu();
    }

    public void setupUI(){
        menuLayout = findViewById(R.id.menuLayout);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog(mContext);
            }
        });
        btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialog(mContext);
            }
        });
        btn_contacts = findViewById(R.id.btn_contacts);
        btn_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContactsDialog(mContext);
            }
        });
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
        activityTitle.setText("Settings");
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
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
            }
        });
        btnMenuNewsFeed = findViewById(R.id.btnMenuNewsFeed);
        btnMenuNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, NewsFeedActivity.class));
                finish();
            }
        });
        btnMenuSettings = findViewById(R.id.btnMenuSettings);
        btnMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                btnMenu.performClick();
                finish();

            }
        });
        btnMenuHelpAboutUs = findViewById(R.id.btnMenuHelpAboutUs);
        btnMenuHelpAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, HelpContactActivity.class));
                finish();
            }
        });
        btnMenuLogout = findViewById(R.id.btnMenuLogout);
        btnMenuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Working", Toast.LENGTH_LONG).show();
                logoutUser();
                SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, SignInActivity.class));
                finish();
            }
        });

    }


    public void updatePassword(String password){
        mAuth.getCurrentUser().updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Password changes successfully", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(mContext, "password not changed", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void updateICE(String ice1, String ice2, String ice3, Context context) {
    }
    private void updateProfile(String name, String mobile, Context context) {
    }

    public void showChangePasswordDialog(final Context context){
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView  = null;
            promptsView  = li.inflate(R.layout.dialog_change_password, null);

            final EditText etCurrentPassword = promptsView.findViewById(R.id.et_current_password);
            final EditText etNewPassword = promptsView.findViewById(R.id.et_new_password);
            final EditText etNewConfirmPassword = promptsView.findViewById(R.id.et_new_confirm_password);
            final Button   btnCancel = promptsView.findViewById(R.id.btn_cancel);
            final Button   btnConfirm = promptsView.findViewById(R.id.btn_confirm);


            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(true);
            // retrieve display dimensions
            Rect displayRectangle = new Rect();

            promptsView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
            promptsView.setMinimumHeight((int) (displayRectangle.height() * 0.01f));

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateChangePasswordForm(etCurrentPassword.getText().toString(), etNewPassword.getText().toString(), etNewConfirmPassword.getText().toString(), context)){
                        updatePassword(tvEmail.getText().toString());
                        alertDialog.dismiss();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            // show it
            alertDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
        }
    }

    public void showContactsDialog(final Context context){
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView  = null;
            promptsView  = li.inflate(R.layout.dialog_contacts, null);

            final EditText et_ice1 = promptsView.findViewById(R.id.et_ice1);
            final EditText et_ice2 = promptsView.findViewById(R.id.et_ice2);
            final EditText et_ice3 = promptsView.findViewById(R.id.et_ice3);
            final Button   btnCancel = promptsView.findViewById(R.id.btn_cancel);
            final Button   btnConfirm = promptsView.findViewById(R.id.btn_confirm);

            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(true);
            // retrieve display dimensions
            Rect displayRectangle = new Rect();

            promptsView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
            promptsView.setMinimumHeight((int) (displayRectangle.height() * 0.01f));

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateICE(et_ice1.getText().toString(), et_ice2.getText().toString(), et_ice3.getText().toString(), context);
                    alertDialog.dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            // show it
            alertDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
        }
    }



    public void showEditProfileDialog(final Context context){
        try {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView  = null;
            promptsView  = li.inflate(R.layout.dialog_change_profile, null);

            final EditText et_name = promptsView.findViewById(R.id.et_name);
            final EditText et_mobile_number = promptsView.findViewById(R.id.et_mobile_number);

            final Button   btnCancel = promptsView.findViewById(R.id.btn_cancel);
            final Button   btnConfirm = promptsView.findViewById(R.id.btn_confirm);

            final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(true);
            // retrieve display dimensions
            Rect displayRectangle = new Rect();

            promptsView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
            promptsView.setMinimumHeight((int) (displayRectangle.height() * 0.01f));

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateChangeProfileForm(et_name.getText().toString(), et_mobile_number.getText().toString(), context)){
                        updateProfile(et_name.getText().toString(), et_mobile_number.getText().toString(), context);
                        alertDialog.dismiss();
                    }
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            // show it
            alertDialog.show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e);
        }
    }
}
