package com.histler.weather.remote.api.openweather.daily;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Wind implements Serializable {
    private float speed;
    @SerializedName("deg")
    private float degree;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }
}
