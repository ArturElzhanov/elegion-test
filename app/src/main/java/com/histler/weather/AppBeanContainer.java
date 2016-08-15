package com.histler.weather;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.histler.appbase.dao.CreateTableDao;
import com.histler.common.serializer.LongDateSerializer;
import com.histler.weather.dao.CityDao;
import com.histler.weather.dao.DailyWeatherDao;
import com.histler.weather.dao.ForecastDao;
import com.histler.weather.dao.WeatherDao;
import com.histler.weather.remote.OpenWeatherRestService;
import com.histler.weather.service.CityService;
import com.histler.weather.service.OpenWeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public enum AppBeanContainer {
    INSTANCE;

    private final List<CreateTableDao> mAllDaos = new ArrayList<>();
    private CityDao mCityDao;
    private DailyWeatherDao mDailyWeatherDao;
    private WeatherDao mWeatherDao;
    private ForecastDao mForecastDao;

    private OpenWeatherRestService mOpenWeatherRestService;
    private Retrofit mRetrofit;

    private OpenWeatherService mOpenWeatherService;
    private CityService mCityService;

    AppBeanContainer() {
        mCityDao = new CityDao();
        mWeatherDao = new WeatherDao();
        mDailyWeatherDao = new DailyWeatherDao();
        mForecastDao = new ForecastDao();
        mAllDaos.add(mCityDao);
        mAllDaos.add(mWeatherDao);
        mAllDaos.add(mDailyWeatherDao);
        mAllDaos.add(mForecastDao);
    }

    /*todo move endpoint to properties file, so we can easily change it for debug, if needed. remove appid from here*/
    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .addQueryParameter("appid", "b8aa3787aeb879559ce3a78cf053d318")//use our own openweather appid
                            .addQueryParameter("units", "metric")
                            .addQueryParameter("mode", "json")
                            .addQueryParameter("lang", "ru")
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new LongDateSerializer()).create();
            mRetrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl("http://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }

    public OpenWeatherRestService getOpenWeatherRestService() {
        if (mOpenWeatherRestService == null) {
            mOpenWeatherRestService = getRetrofit().create(OpenWeatherRestService.class);
        }
        return mOpenWeatherRestService;
    }

    public List<CreateTableDao> getAllDaos() {
        return mAllDaos;
    }

    public CityDao getCityDao() {
        return mCityDao;
    }

    public DailyWeatherDao getDailyWeatherDao() {
        return mDailyWeatherDao;
    }

    public WeatherDao getWeatherDao() {
        return mWeatherDao;
    }

    public ForecastDao getForecastDao() {
        return mForecastDao;
    }

    public OpenWeatherService getOpenWeatherService() {
        if (mOpenWeatherService == null) {
            mOpenWeatherService = new OpenWeatherService();
        }
        return mOpenWeatherService;
    }

    public CityService getCityService() {
        if (mCityService == null) {
            mCityService = new CityService();
        }
        return mCityService;
    }
}
