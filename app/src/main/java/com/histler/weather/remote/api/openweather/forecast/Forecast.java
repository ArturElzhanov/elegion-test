package com.histler.weather.remote.api.openweather.forecast;

import com.google.gson.annotations.SerializedName;
import com.histler.appbase.entity.IHasId;
import com.histler.weather.remote.api.openweather.Weather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class Forecast implements Serializable, IHasId {
    @SerializedName("dt")
    private Date date;
    @SerializedName("temp")
    private Temperature temperature;
    private float pressure;
    //percentage
    private long humidity;
    @SerializedName("weather")
    private java.util.List<Weather> weathers;
    private float speed;
    @SerializedName("deg")
    private int degree;
    private int clouds;
    private long id;
    private long weatherId;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public java.util.List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(java.util.List<Weather> weathers) {
        this.weathers = weathers;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(long weatherId) {
        this.weatherId = weatherId;
    }

    public static class List extends ArrayList<Forecast> {
        public List(int capacity) {
            super(capacity);
        }

        public List() {
        }

        public List(Collection<? extends Forecast> collection) {
            super(collection);
        }
    }

}
