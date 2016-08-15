package com.histler.weather.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.histler.appbase.dao.GeneralDao;
import com.histler.weather.remote.api.openweather.forecast.Forecast;
import com.histler.weather.remote.api.openweather.forecast.Temperature;

import java.util.Calendar;
import java.util.Date;


public class ForecastDao extends GeneralDao<Forecast> {
    public static final String COLUMN_DATETIME = "dt";
    public static final String COLUMN_TEMP_MIN = "temp_min";
    public static final String COLUMN_TEMP_MAX = "temp_max";
    public static final String COLUMN_PRESSURE = "pressure";
    public static final String COLUMN_HUMIDITY = "humidity";
    public static final String COLUMN_WEATHER_ID = "weather_id";
    public static final String COLUMN_WIND_SPEED = "wind_speed";
    public static final String COLUMN_WIND_DEGREE = "wind_degree";
    public static final String COLUMN_CLOUDS = "clouds";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_DATETIME,
            COLUMN_TEMP_MIN,
            COLUMN_TEMP_MAX,
            COLUMN_PRESSURE,
            COLUMN_HUMIDITY,
            COLUMN_WEATHER_ID,
            COLUMN_WIND_SPEED,
            COLUMN_WIND_DEGREE,
            COLUMN_CLOUDS
    };

    @Override
    protected String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public String getNoTableNameCreateTableQuery() {
        return "( " +
                COLUMN_ID + " integer not null," +
                COLUMN_DATETIME + " integer default 0," +
                COLUMN_TEMP_MIN + " integer default 0," +
                COLUMN_TEMP_MAX + " integer default 0," +
                COLUMN_PRESSURE + " integer default 0," +
                COLUMN_HUMIDITY + " integer default 0," +
                COLUMN_WEATHER_ID + " integer default 0," +
                COLUMN_WIND_SPEED + " integer default 0," +
                COLUMN_WIND_DEGREE + " integer default 0," +
                COLUMN_CLOUDS + " integer default 0);";
    }

    @Override
    public Forecast cursorToEntity(Cursor cursor, int index) {
        Forecast entity = new Forecast();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setDate(new Date(cursor.getLong(i)));
        i++;
        Temperature temperature = new Temperature();
        temperature.setMin(cursor.getFloat(i));
        i++;
        temperature.setMax(cursor.getFloat(i));
        entity.setTemperature(temperature);
        i++;
        entity.setPressure(cursor.getFloat(i));
        i++;
        entity.setHumidity(cursor.getLong(i));
        i++;
        entity.setWeatherId(cursor.getLong(i));
        i++;
        entity.setSpeed(cursor.getFloat(i));
        i++;
        entity.setDegree(cursor.getInt(i));
        i++;
        entity.setClouds(cursor.getInt(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Forecast entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_DATETIME, entity.getDate().getTime());
        if (entity.getTemperature() != null) {
            values.put(COLUMN_TEMP_MIN, entity.getTemperature().getMin());
            values.put(COLUMN_TEMP_MAX, entity.getTemperature().getMax());
        } else {
            values.putNull(COLUMN_TEMP_MIN);
            values.putNull(COLUMN_TEMP_MAX);
        }
        values.put(COLUMN_PRESSURE, entity.getPressure());
        values.put(COLUMN_HUMIDITY, entity.getHumidity());
        if (entity.getWeathers() != null && !entity.getWeathers().isEmpty()) {
            values.put(COLUMN_WEATHER_ID, entity.getWeathers().get(0).getId());
        } else {
            values.putNull(COLUMN_WEATHER_ID);
        }
        values.put(COLUMN_WIND_SPEED, entity.getSpeed());
        values.put(COLUMN_WIND_DEGREE, entity.getDegree());
        values.put(COLUMN_CLOUDS, entity.getClouds());
        return values;
    }

    public Forecast.List getForecastForCityId(SQLiteDatabase database, long cityId) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long datetime = calendar.getTimeInMillis();
        Cursor cursor = database.query(
                getTableName(),
                getAllColumns(),
                COLUMN_ID + "=? and " + COLUMN_DATETIME + ">=?", new String[]{String.valueOf(cityId), String.valueOf(datetime)}, null, null, getOrderBy());
        try {
            Forecast.List entities = new Forecast.List(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    Forecast entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    public boolean delete(SQLiteDatabase database, long cityId) {
        return database.delete(getTableName(), COLUMN_ID + "=?", new String[]{String.valueOf(cityId)}) > 0;
    }

    @Override
    public String getOrderBy() {
        return COLUMN_DATETIME;
    }
}
