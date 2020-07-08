package com.appmate.watchout;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by Zain Ishtiaq on 4/25/2019.
 */

public class MyApp extends Application {

    private String TAG = "Ertugrul Android";
//    private static Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase Auth
//        new Instabug.Builder(this, "4d1116913f62c1b49eb7032f06c7e7a6")
//                .setInvocationEvents(
//                        InstabugInvocationEvent.SHAKE,
//                        InstabugInvocationEvent.FLOATING_BUTTON)
//                .build();
//        new Instabug.Builder(this, "d68ee581f05c099cdf3942db5630fc1d").build();


        // Obtain the FirebaseAnalytics instance.
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        if(BuildConfig.BASE_URL.equals("https://beta.hello-demo.com/api") || BuildConfig.BASE_URL.equals("https://web.hello-demo.com/api")) {
//            Intercom.initialize(this, "android_sdk-6f910a2cd2ef34e14cec0ff45c84787c69f6e7bc", "wy6u5pym");
//        }

        /**************************************************************/
        // Use the Sentry DSN (client key) from the Project Settings page on Sentry
//        String sentryDsn = "https://5737cac6a63443d098eb422e9c4ffa30@sentry.io/1425283";
//        Sentry.init(sentryDsn, new AndroidSentryClientFactory(this));

        // Alternatively, if you configured your DSN in a `sentry.properties`
        // file (see the configuration documentation).
//        Sentry.init(new AndroidSentryClientFactory(context));

        //Internet Connectivity
//        InternetAvailabilityChecker.init(this);

        /***********************************************************/
        //TypefaceUtil.overrideFont(getApplicationContext(), "normal", "fonts/MemphisLTStd-Medium.otf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
//        try {
//            socket = IO.socket(BuildConfig.SOCKET_URL);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
    }

    public static void logoutUser(){
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }
//
//    public static Socket getSocket() {
//        if (!socket.connected()) {
//            socket.connect();
//        }
//        return socket;
//    }
}
