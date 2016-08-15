package com.histler.appbase.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Badr
 * on 29.05.2016 2:56.
 */
public final class NetworkUtils {
    @SuppressWarnings("deprecation")
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo[] allNetworkInfos = connectivityManager.getAllNetworkInfo();
        for (final NetworkInfo networkInfo : allNetworkInfos) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED || networkInfo.getState() == NetworkInfo.State.CONNECTING) {
                return true;
            }
        }
        return false;
    }
}
