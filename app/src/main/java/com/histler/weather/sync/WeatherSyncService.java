package com.histler.weather.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class WeatherSyncService extends Service {
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();
    // Storage for an instance of the sync adapter
    private static WeatherThreadedSyncAdapter sWeatherSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sWeatherSyncAdapter == null) {
                sWeatherSyncAdapter = new WeatherThreadedSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return sWeatherSyncAdapter.getSyncAdapterBinder();
    }
}
