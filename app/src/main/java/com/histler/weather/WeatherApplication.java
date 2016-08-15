package com.histler.weather;

import android.app.Application;


public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Initializer.initialize();
    }

}
