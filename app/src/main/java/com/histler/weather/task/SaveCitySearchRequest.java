package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.service.OpenWeatherService;

import java.util.Collections;


public class SaveCitySearchRequest extends TaskRequest<CityDailyWeather> {

    private Context mContext;
    private CityDailyWeather mEntity;

    public SaveCitySearchRequest(Context context, CityDailyWeather cityDailyWeather) {
        super(CityDailyWeather.class);
        mContext = context.getApplicationContext();
        mEntity = cityDailyWeather;
    }

    @Override
    public CityDailyWeather loadData() throws Exception {
        OpenWeatherService service = AppBeanContainer.INSTANCE.getOpenWeatherService();
        service.saveCityDailyWeathers(mContext, Collections.singletonList(mEntity));
        return mEntity;
    }
}
