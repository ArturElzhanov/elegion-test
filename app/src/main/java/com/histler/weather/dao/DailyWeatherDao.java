package com.histler.weather.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.histler.appbase.dao.GeneralDao;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.DailyTemperature;

import java.util.Date;


public class DailyWeatherDao extends GeneralDao<CityDailyWeather> {
    public static final String COLUMN_DATETIME = "dt";
    public static final String COLUMN_TEMP = "temp";
    public static final String COLUMN_MIN_TEMP = "temp_min";
    public static final String COLUMN_MAX_TEMP = "temp_max";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_WEATHER_ID = "weather_id";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_DATETIME,
            COLUMN_TEMP,
            COLUMN_MIN_TEMP,
            COLUMN_MAX_TEMP,
            COLUMN_PRESSURE,
            COLUMN_HUMIDITY,
            COLUMN_WEATHER_ID
    };

    @Override
    protected String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public String getNoTableNameCreateTableQuery() {
        return "( " +
                COLUMN_ID + " integer primary key," +
                COLUMN_DATETIME + " integer default 0," +
                COLUMN_TEMP + " integer default 0," +
                COLUMN_MIN_TEMP + " integer default 0," +
                COLUMN_MAX_TEMP + " integer default 0," +
                COLUMN_PRESSURE + " integer default 0," +
                COLUMN_HUMIDITY + " integer default 0," +
                COLUMN_WEATHER_ID + " integer default 0);";
    }

    @Override
    public CityDailyWeather cursorToEntity(Cursor cursor, int index) {
        CityDailyWeather entity = new CityDailyWeather();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setDate(new Date(cursor.getLong(i)));
        i++;
        DailyTemperature temp = new DailyTemperature();
        temp.setTemperature(cursor.getFloat(i));
        i++;
        temp.setMinTemperature(cursor.getFloat(i));
        i++;
        temp.setMaxTemperature(cursor.getFloat(i));
        i++;
        temp.setPressure(cursor.getFloat(i));
        i++;
        temp.setHumidity(cursor.getFloat(i));
        entity.setTemperature(temp);
        i++;
        entity.setWeatherId(cursor.getLong(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(CityDailyWeather entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_DATETIME, entity.getDate().getTime());
        if (entity.getTemperature() != null) {
            DailyTemperature temp = entity.getTemperature();
            values.put(COLUMN_TEMP, temp.getTemperature());
            values.put(COLUMN_MIN_TEMP, temp.getMinTemperature());
            values.put(COLUMN_MAX_TEMP, temp.getMaxTemperature());
            values.put(COLUMN_PRESSURE, temp.getPressure());
            values.put(COLUMN_HUMIDITY, temp.getHumidity());
        } else {
            values.putNull(COLUMN_TEMP);
            values.putNull(COLUMN_MIN_TEMP);
            values.putNull(COLUMN_MAX_TEMP);
            values.putNull(COLUMN_PRESSURE);
            values.putNull(COLUMN_HUMIDITY);
        }
        if (entity.getWeathers() != null && !entity.getWeathers().isEmpty()) {
            values.put(COLUMN_WEATHER_ID, entity.getWeathers().get(0).getId());
        } else {
            values.putNull(COLUMN_WEATHER_ID);
        }

        return values;
    }
}
