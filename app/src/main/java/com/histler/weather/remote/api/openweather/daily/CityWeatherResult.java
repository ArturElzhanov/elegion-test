package com.histler.weather.remote.api.openweather.daily;

import com.histler.weather.remote.api.openweather.OpenWeatherResult;


public class CityWeatherResult extends OpenWeatherResult<CityDailyWeather, String> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
