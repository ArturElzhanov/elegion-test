package com.histler.weather.task;

import android.content.Context;

import com.histler.appbase.util.robospice.TaskRequest;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.OpenWeatherRestService;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.remote.api.openweather.forecast.CityForecastResult;
import com.histler.weather.remote.api.openweather.forecast.Forecast;
import com.histler.weather.service.OpenWeatherService;

import java.util.List;

import retrofit2.Response;


public class CityForecastRequest extends TaskRequest<Forecast.List> {
    private Context mContext;
    private City mCity;

    public CityForecastRequest(Context context, City city) {
        super(Forecast.List.class);
        mContext = context.getApplicationContext();
        mCity = city;
    }

    @Override
    public Forecast.List loadData() throws Exception {
        OpenWeatherRestService restService = AppBeanContainer.INSTANCE.getOpenWeatherRestService();
        Response<CityForecastResult> response = restService.getWeekForecast(mCity.getId()).execute();
        if (response.isSuccessful()) {
            List<Forecast> list = response.body().getList();
            OpenWeatherService service = AppBeanContainer.INSTANCE.getOpenWeatherService();
            service.saveCityForecast(mContext, mCity.getId(), list);
            return service.getCityForecast(mContext, mCity.getId());
        } else {
            throw new Exception(response.errorBody().string());
        }
    }
}
