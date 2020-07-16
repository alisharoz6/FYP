package com.appmate.watchout.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import static com.appmate.watchout.activity.SplashActivity.mAuth;
import static com.appmate.watchout.util.AppUtil.validateSignUpForm;


public class SignUpActivity extends AppCompatActivity {

    private String TAG = "SignUpActivity";
    private Context mContext;
    private EditText etName,etEmail,etPassword,etConfirmPassword,etMobile;
    private TextView btnSignIn,btnSignUp;
    private View loadingLayout;
    private SpinKitView progressBar;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_signup);
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
        etName =  findViewById(R.id.et_name);
        etEmail =  findViewById(R.id.et_email);
        etPassword =  findViewById(R.id.et_password);
        etConfirmPassword =  findViewById(R.id.et_confirm_password);
        etMobile =  findViewById(R.id.et_mobile_number);

        btnSignIn =  findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSignUp =  findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateSignUpForm(etName.getText().toString(), etEmail.getText().toString() , etPassword.getText().toString(), etConfirmPassword.getText().toString() ,mContext)){
                    performSignUp(etName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                }
            }
        });

    }

    public void performSignUp(final String userName , String email, String password){
        showProgress();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                                Toast.makeText(mContext, "Congrats! SignUp Successful",
                                                        Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                            else{
                                                Log.d(TAG, "Update Failed.");
                                            }
                                        }
                                    });
//                            updateUI(user);
                        } else {
                            hideProgress();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
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

}