package com.histler.weather.remote.api.openweather.daily;

import java.io.Serializable;


public class Sys implements Serializable {
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
