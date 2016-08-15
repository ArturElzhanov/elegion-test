package com.histler.weather.remote.api.openweather.forecast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Temperature implements Serializable {
    private float day;
    private float min;
    private float max;
    private float night;
    @SerializedName("eve")
    private float evening;
    @SerializedName("morn")
    private float morning;

    public float getDay() {
        return day;
    }

    public void setDay(float day) {
        this.day = day;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getNight() {
        return night;
    }

    public void setNight(float night) {
        this.night = night;
    }

    public float getEvening() {
        return evening;
    }

    public void setEvening(float evening) {
        this.evening = evening;
    }

    public float getMorning() {
        return morning;
    }

    public void setMorning(float morning) {
        this.morning = morning;
    }
}
