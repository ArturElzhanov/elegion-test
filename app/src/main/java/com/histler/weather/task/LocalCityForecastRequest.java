package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.remote.api.openweather.forecast.Forecast;


public class LocalCityForecastRequest extends TaskRequest<Forecast.List> {
    private Context mContext;
    private City mCity;

    public LocalCityForecastRequest(Context context, City city) {
        super(Forecast.List.class);
        mContext = context.getApplicationContext();
        mCity = city;
    }

    @Override
    public Forecast.List loadData() throws Exception {
        return AppBeanContainer.INSTANCE.getOpenWeatherService().getCityForecast(mContext, mCity.getId());
    }
}
