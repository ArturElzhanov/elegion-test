package com.histler.weather.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.histler.appbase.dao.DatabaseManager;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.dao.CityDao;
import com.histler.weather.dao.DailyWeatherDao;
import com.histler.weather.dao.ForecastDao;
import com.histler.weather.dao.WeatherDao;
import com.histler.weather.remote.api.openweather.Weather;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.Sys;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.remote.api.openweather.forecast.Forecast;

import java.util.ArrayList;
import java.util.List;


public class OpenWeatherService {
    public CityDailyWeather getCityDailyWeather(Context context, long cityId) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        DailyWeatherDao dailyWeatherDao = appBeanContainer.getDailyWeatherDao();
        WeatherDao weatherDao = appBeanContainer.getWeatherDao();
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            City city = appBeanContainer.getCityDao().getById(database, cityId);
            if (city != null) {
                CityDailyWeather cityDailyWeather = dailyWeatherDao.getById(database, cityId);
                if (cityDailyWeather == null) {
                    cityDailyWeather = new CityDailyWeather();
                }
                cityDailyWeather.setCityName(city.getName());
                Sys sys = new Sys();
                sys.setCountry(city.getCountry());
                cityDailyWeather.setSys(sys);
                cityDailyWeather.setCoordinate(city.getCoordinate());
                List<Weather> weathers = new ArrayList<>();
                Weather firstWeather = weatherDao.getById(database, cityDailyWeather.getWeatherId());
                weathers.add(firstWeather);
                cityDailyWeather.setWeathers(weathers);
                return cityDailyWeather;
            }
            return null;
        } finally {
            manager.closeDatabase();
        }
    }
    public CityDailyWeather.List getCityDailyWeathers(Context context) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        DailyWeatherDao dailyWeatherDao = appBeanContainer.getDailyWeatherDao();
        WeatherDao weatherDao = appBeanContainer.getWeatherDao();
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            List<City> cities = appBeanContainer.getCityDao().getAllEntities(database);
            CityDailyWeather.List list = new CityDailyWeather.List();
            if (cities != null && !cities.isEmpty()) {
                for (City city : cities) {
                    CityDailyWeather cityDailyWeather = dailyWeatherDao.getById(database, city.getId());
                    if (cityDailyWeather == null) {
                        cityDailyWeather = new CityDailyWeather();
                    }
                    cityDailyWeather.setCityName(city.getName());
                    Sys sys = new Sys();
                    sys.setCountry(city.getCountry());
                    cityDailyWeather.setSys(sys);
                    cityDailyWeather.setCoordinate(city.getCoordinate());
                    List<Weather> weathers = new ArrayList<>();
                    Weather firstWeather = weatherDao.getById(database, cityDailyWeather.getWeatherId());
                    weathers.add(firstWeather);
                    cityDailyWeather.setWeathers(weathers);
                    list.add(cityDailyWeather);
                }
            }
            return list;
        } finally {
            manager.closeDatabase();
        }
    }

    public void saveCityDailyWeathers(Context context, List<CityDailyWeather> cityDailyWeathers) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        CityDao cityDao = appBeanContainer.getCityDao();
        WeatherDao weatherDao = appBeanContainer.getWeatherDao();
        DailyWeatherDao dailyWeatherDao = appBeanContainer.getDailyWeatherDao();
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            for (CityDailyWeather cityDailyWeather : cityDailyWeathers) {
                if (cityDao.getById(database, cityDailyWeather.getId()) == null) {
                    City city = new City();
                    city.setId(cityDailyWeather.getId());
                    city.setCountry(cityDailyWeather.getCountryName());
                    city.setCoordinate(cityDailyWeather.getCoordinate());
                    city.setName(cityDailyWeather.getCityName());
                    cityDao.save(database, city);
                }
                if (cityDailyWeather.getWeathers() != null && !cityDailyWeather.getWeathers().isEmpty()) {
                    for (Weather weather : cityDailyWeather.getWeathers()) {
                        if (weatherDao.getById(database, weather.getId()) == null) {
                            weatherDao.save(database, weather);
                        }
                    }
                    cityDailyWeather.setWeatherId(cityDailyWeather.getWeathers().get(0).getId());
                }
                dailyWeatherDao.delete(database, cityDailyWeather);
                dailyWeatherDao.save(database, cityDailyWeather);
            }
        } finally {
            manager.closeDatabase();
        }
    }

    public Forecast.List getCityForecast(Context context, long cityId) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        ForecastDao forecastDao = appBeanContainer.getForecastDao();
        WeatherDao weatherDao = appBeanContainer.getWeatherDao();
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            Forecast.List list = forecastDao.getForecastForCityId(database, cityId);
            for (Forecast forecast : list) {
                List<Weather> weathers = new ArrayList<>();
                Weather firstWeather = weatherDao.getById(database, forecast.getWeatherId());
                weathers.add(firstWeather);
                forecast.setWeathers(weathers);
            }
            return list;
        } finally {
            manager.closeDatabase();
        }
    }

    public void saveCityForecast(Context context, long cityId, List<Forecast> forecasts) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        WeatherDao weatherDao = appBeanContainer.getWeatherDao();
        ForecastDao forecastDao = appBeanContainer.getForecastDao();
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            forecastDao.delete(database, cityId);
            for (Forecast forecast : forecasts) {
                forecast.setId(cityId);
                if (forecast.getWeathers() != null && !forecast.getWeathers().isEmpty()) {
                    for (Weather weather : forecast.getWeathers()) {
                        if (weatherDao.getById(database, weather.getId()) == null) {
                            weatherDao.save(database, weather);
                        }
                    }
                    forecast.setWeatherId(forecast.getWeathers().get(0).getId());
                }
                forecastDao.save(database, forecast);
            }
        } finally {
            manager.closeDatabase();
        }
    }
}
