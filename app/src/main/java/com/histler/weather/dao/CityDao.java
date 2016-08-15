package com.histler.weather.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.histler.appbase.dao.GeneralDao;
import com.histler.weather.remote.api.openweather.Coordinate;
import com.histler.weather.remote.api.openweather.forecast.City;


public class CityDao extends GeneralDao<City> {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_COUNTRY,
            COLUMN_LATITUDE,
            COLUMN_LONGITUDE
    };


    @Override
    protected String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public String getNoTableNameCreateTableQuery() {
        return "( " +
                COLUMN_ID + " integer primary key," +
                COLUMN_NAME + " text default null," +
                COLUMN_COUNTRY + " text default null," +
                COLUMN_LATITUDE + " integer default 0," +
                COLUMN_LONGITUDE + " integer default 0);";
    }

    @Override
    public City cursorToEntity(Cursor cursor, int index) {
        City entity = new City();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setName(cursor.getString(i));
        i++;
        entity.setCountry(cursor.getString(i));
        i++;
        Coordinate coordinate = new Coordinate();
        coordinate.setLatitude(cursor.getFloat(i));
        i++;
        coordinate.setLongitude(cursor.getFloat(i));
        entity.setCoordinate(coordinate);
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(City entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (!TextUtils.isEmpty(entity.getName())) {
            values.put(COLUMN_NAME, entity.getName());
        } else {
            values.putNull(COLUMN_NAME);
        }
        if (!TextUtils.isEmpty(entity.getCountry())) {
            values.put(COLUMN_COUNTRY, entity.getCountry());
        } else {
            values.putNull(COLUMN_COUNTRY);
        }
        if (entity.getCoordinate() != null) {
            values.put(COLUMN_LATITUDE, entity.getCoordinate().getLatitude());
            values.put(COLUMN_LONGITUDE, entity.getCoordinate().getLongitude());
        } else {
            values.putNull(COLUMN_LATITUDE);
            values.putNull(COLUMN_LONGITUDE);
        }
        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        super.onCreate(database);

        City city = new City();
        city.setId(551487);
        city.setName("Kazan");
        city.setCountry("RU");
        city.setCoordinate(new Coordinate(49.12f, 55.79f));
        save(database, city);

        city = new City();
        city.setId(524901);
        city.setName("Moscow");
        city.setCountry("RU");
        city.setCoordinate(new Coordinate(37.62f, 55.75f));
        save(database, city);

        city = new City();
        city.setId(541792);
        city.setName("Krasnyye Chelny");
        city.setCountry("RU");
        city.setCoordinate(new Coordinate(52.35f, 55.7f));
        save(database, city);

        city = new City();
        city.setId(519690);
        city.setName("Novaya Gollandiya");
        city.setCountry("RU");
        city.setCoordinate(new Coordinate(30.29f, 59.93f));
        save(database, city);

        city = new City();
        city.setId(491422);
        city.setName("Sochi");
        city.setCountry("RU");
        city.setCoordinate(new Coordinate(39.73f, 43.6f));
        save(database, city);
    }
}
