package com.histler.weather.task;


import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.OpenWeatherRestService;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.CityWeatherResult;

import retrofit2.Response;


public class CitySearchRequest extends TaskRequest<CityDailyWeather.List> {

    private String mSearchQuery;

    public CitySearchRequest(String search) {
        super(CityDailyWeather.List.class);
        mSearchQuery = search;
    }

    @Override
    public CityDailyWeather.List loadData() throws Exception {
        OpenWeatherRestService restService = AppBeanContainer.INSTANCE.getOpenWeatherRestService();
        Response<CityWeatherResult> response = restService.getWeather(mSearchQuery).execute();
        if (response.isSuccessful()) {
            return new CityDailyWeather.List(response.body().getList());
        } else {
            throw new Exception(response.errorBody().string());
        }
    }
}
