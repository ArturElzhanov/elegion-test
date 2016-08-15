package com.histler.weather.remote.api.openweather.daily;

import com.google.gson.annotations.SerializedName;
import com.histler.appbase.entity.IHasId;
import com.histler.weather.remote.api.openweather.Coordinate;
import com.histler.weather.remote.api.openweather.Weather;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class CityDailyWeather implements Serializable, IHasId {
    private long id;
    @SerializedName("dt")
    private Date date;
    @SerializedName("name")
    private String cityName;
    @SerializedName("coord")
    private Coordinate coordinate;
    @SerializedName("main")
    private DailyTemperature temperature;
    private Sys sys;
    private Clouds clouds;
    @SerializedName("weather")
    private java.util.List<Weather> weathers;
    private long weatherId;

    public long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(long weatherId) {
        this.weatherId = weatherId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return getSys() != null ? getSys().getCountry() : null;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public DailyTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(DailyTemperature temperature) {
        this.temperature = temperature;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public java.util.List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(java.util.List<Weather> weathers) {
        this.weathers = weathers;
    }

    public static class List extends ArrayList<CityDailyWeather> {
        public List(int capacity) {
            super(capacity);
        }

        public List() {
        }

        public List(Collection<? extends CityDailyWeather> collection) {
            super(collection);
        }
    }
}
