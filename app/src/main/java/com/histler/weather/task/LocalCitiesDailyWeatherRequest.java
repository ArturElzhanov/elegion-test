package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;


public class LocalCitiesDailyWeatherRequest extends TaskRequest<CityDailyWeather.List> {
    private Context mContext;

    public LocalCitiesDailyWeatherRequest(Context context) {
        super(CityDailyWeather.List.class);
        mContext = context.getApplicationContext();
    }

    @Override
    public CityDailyWeather.List loadData() throws Exception {
        return AppBeanContainer.INSTANCE.getOpenWeatherService().getCityDailyWeathers(mContext);
    }
}
