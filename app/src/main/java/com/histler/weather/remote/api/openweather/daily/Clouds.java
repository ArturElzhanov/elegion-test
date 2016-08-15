package com.histler.weather.remote.api.openweather.daily;

import java.io.Serializable;


public class Clouds implements Serializable {
    private int all;

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
