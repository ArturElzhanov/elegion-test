package com.histler.weather.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.histler.appbase.dao.DatabaseManager;
import com.histler.weather.AppBeanContainer;
import com.histler.weather.remote.api.openweather.forecast.City;

import java.util.ArrayList;
import java.util.List;


public class CityService {
    public List<Long> getCityIds(Context context) {
        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openWritableDatabase();
        try {
            List<City> list = appBeanContainer.getCityDao().getAllEntities(database);
            List<Long> ids = new ArrayList<>();
            if (list != null && !list.isEmpty()) {
                for (City entity : list) {
                    ids.add(entity.getId());
                }
            }
            return ids;
        } finally {
            manager.closeDatabase();
        }
    }
}
