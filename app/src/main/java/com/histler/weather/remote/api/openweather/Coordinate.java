package com.histler.weather.remote.api.openweather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Coordinate implements Serializable {
    @SerializedName("lon")
    private float longitude;
    @SerializedName("lat")
    private float latitude;

    public Coordinate(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinate() {
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
}
