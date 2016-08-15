package com.histler.weather.remote;

import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.CityWeatherResult;
import com.histler.weather.remote.api.openweather.forecast.CityForecastResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface OpenWeatherRestService {
    @GET("find")
    Call<CityWeatherResult> getWeather(@Query("q") String cityName);

    @GET("weather")
    Call<CityDailyWeather> getWeather(@Query("id") long cityId);

    @GET("forecast/daily?cnt=7")
    Call<CityForecastResult> getWeekForecast(@Query("id") long cityId);

    @GET("group")
    Call<CityWeatherResult> getDailyWeathers(@Query("id") String idList);
}