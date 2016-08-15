package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.OpenWeatherRestService;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.service.OpenWeatherService;

import java.util.Collections;

import retrofit2.Response;


public class CityDailyWeatherRequest extends TaskRequest<CityDailyWeather> {
    private Context mContext;
    private City mCity;

    public CityDailyWeatherRequest(Context context, City city) {
        super(CityDailyWeather.class);
        mContext = context.getApplicationContext();
        mCity = city;
    }

    @Override
    public CityDailyWeather loadData() throws Exception {
        OpenWeatherRestService restService = AppBeanContainer.INSTANCE.getOpenWeatherRestService();
        Response<CityDailyWeather> response = restService.getWeather(mCity.getId()).execute();
        if (response.isSuccessful()) {
            CityDailyWeather cityDailyWeather = response.body();
            OpenWeatherService service = AppBeanContainer.INSTANCE.getOpenWeatherService();
            service.saveCityDailyWeathers(mContext, Collections.singletonList(cityDailyWeather));
            return service.getCityDailyWeather(mContext, mCity.getId());
        } else {
            throw new Exception(response.errorBody().string());
        }
    }
}
