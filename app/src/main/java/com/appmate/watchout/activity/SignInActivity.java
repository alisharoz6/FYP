package com.appmate.watchout.activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appmate.watchout.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.AppUtil.validateSignInForm;


public class SignInActivity extends AppCompatActivity {

    private String TAG = "SignInActivity";
    private Context mContext;
    private EditText etEmail,etPassword;
    private TextView btnForgotPassword,btnLogin,btnSignUp;
    private View loadingLayout;
    private SpinKitView progressBar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_signin);
        mContext = this;
        setupUI();
        setupProgressBar();
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
        loadingLayout = findViewById(R.id.loadingLayout);
        etEmail =  findViewById(R.id.et_email);
        etPassword =  findViewById(R.id.et_password);
        btnForgotPassword =  findViewById(R.id.btn_forgot_password);
        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPasswordDialog(mContext);
            }
        });
        btnLogin =  findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateSignInForm(etEmail.getText().toString() , etPassword.getText().toString() ,mContext)){
                    performSignIn(etEmail.getText().toString(),etPassword.getText().toString());
                }
            }
        });
        btnSignUp =  findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInActivity.this.startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });

    }

    public void performSignIn(String email, String password){
        showProgress();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgress();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            SignInActivity.this.startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgress();
                Toast.makeText(mContext, "Error Occurred.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void resetPassword(String email){
        showProgress();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                hideProgress();
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Reset link sent to your email", Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(mContext, "Unable to send reset mail", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                hideProgress();
                Toast.makeText(mContext, "Error Occurred", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    public void showPasswordDialog(final Context context){

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView  = null;
            promptsView  = li.inflate(R.layout.dialog_forgot_password, null);

            final TextView tvEmail = promptsView.findViewById(R.id.tv_email);
            Button btnReset = promptsView.findViewById(R.id.btn_reset);

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
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetPassword(tvEmail.getText().toString());
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