package com.histler.weather.remote.api.openweather.forecast;

import com.google.gson.annotations.SerializedName;
import com.histler.weather.remote.api.openweather.OpenWeatherResult;


public class CityForecastResult extends OpenWeatherResult<Forecast, Float> {
    private City city;
    @SerializedName("cnt")
    private int count;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
