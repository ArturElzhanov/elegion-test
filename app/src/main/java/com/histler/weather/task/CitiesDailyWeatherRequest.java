package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.OpenWeatherRestService;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.CityWeatherResult;
import com.histler.weather.service.OpenWeatherService;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import retrofit2.Response;


public class CitiesDailyWeatherRequest extends TaskRequest<CityDailyWeather.List> {

    private Context mContext;

    public CitiesDailyWeatherRequest(Context context) {
        super(CityDailyWeather.List.class);
        mContext = context.getApplicationContext();
    }

    @Override
    public CityDailyWeather.List loadData() throws Exception {
        List<Long> ids = AppBeanContainer.INSTANCE.getCityService().getCityIds(mContext);
        OpenWeatherRestService restService = AppBeanContainer.INSTANCE.getOpenWeatherRestService();
        Response<CityWeatherResult> response = restService.getDailyWeathers(StringUtils.join(ids, ',')).execute();
        if (response.isSuccessful()) {
            List<CityDailyWeather> list = response.body().getList();
            OpenWeatherService service = AppBeanContainer.INSTANCE.getOpenWeatherService();
            service.saveCityDailyWeathers(mContext, list);
            return service.getCityDailyWeathers(mContext);
        } else {
            throw new Exception(response.errorBody().string());
        }
    }
}
