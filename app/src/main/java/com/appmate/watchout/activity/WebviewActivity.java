package com.appmate.watchout.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.appmate.watchout.R;

public class WebviewActivity extends AppCompatActivity  {
    private String TAG = "WebviewActivity";

    private WebView myWebView;
    Context mContext;
    String password;

    ProgressDialog progressBar;
    private AlertDialog alertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);
        mContext =  this;
        myWebView = findViewById(R.id.activity_main_webview);
        progressBar = new ProgressDialog(WebviewActivity.this);
        progressBar.setMessage("Please wait...");
        String link = getIntent().getStringExtra("link");
        setWebView(link);
        System.out.println("Url ::"+link);
    }



//    @Override
//    public void onBackPressed(){
//        if(alertDialog != null && alertDialog.isShowing()){
//            alertDialog.dismiss();
//        }
//        if(myWebView.canGoBack()){
//            myWebView.goBack();
//        }else {
//            super.onBackPressed();
//        }
//    }

    //Added Method
    private void setWebView(String url) {
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setMediaPlaybackRequiresUserGesture(true);
        myWebView.setWebViewClient(new WebViewClient());
//        myWebView.setInitialScale(1);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!progressBar.isShowing()) {
                    progressBar.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }
        });
        myWebView.loadUrl(url);
    }
}
