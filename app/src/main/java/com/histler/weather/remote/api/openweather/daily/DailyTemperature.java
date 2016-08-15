package com.histler.weather.remote.api.openweather.daily;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DailyTemperature implements Serializable {
    @SerializedName("temp")
    private float temperature;
    private float pressure;
    private float humidity;
    @SerializedName("temp_min")
    private float minTemperature;
    @SerializedName("temp_max")
    private float maxTemperature;

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
