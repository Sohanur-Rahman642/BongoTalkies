package com.example.bongotalkies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityUtils {

    private static ConnectivityUtils instance;
    private Context context;

    public ConnectivityUtils(Context context) {
        this.context = context;
    }

    public static ConnectivityUtils getInstance(Context context){
        if (instance == null){
            instance = new ConnectivityUtils(context);
        }
        return instance;
    }


    public boolean isOnline() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = null;
            if (connectivityManager != null) {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Throwable ex){
            ex.printStackTrace();
        }
        return false;
    }

}
