package com.lovelycoding.automobile.util;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyApplication extends Application {
    public static boolean isNetworkAvailable;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        networkAvailable();
    }
    public void networkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        isNetworkAvailable=isConnected;
    }
}
