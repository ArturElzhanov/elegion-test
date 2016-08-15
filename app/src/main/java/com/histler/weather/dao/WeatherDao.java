package com.histler.weather.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.histler.appbase.dao.GeneralDao;
import com.histler.weather.remote.api.openweather.Weather;


public class WeatherDao extends GeneralDao<Weather> {
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ICON = "icon";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_CODE,
            COLUMN_DESCRIPTION,
            COLUMN_ICON
    };

    @Override
    protected String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public String getNoTableNameCreateTableQuery() {
        return "( " +
                COLUMN_ID + " integer primary key," +
                COLUMN_CODE + " text not null," +
                COLUMN_DESCRIPTION + " text not null," +
                COLUMN_ICON + " text not null);";
    }

    @Override
    public Weather cursorToEntity(Cursor cursor, int index) {
        Weather entity = new Weather();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setCode(cursor.getString(i));
        i++;
        entity.setDescription(cursor.getString(i));
        i++;
        entity.setIcon(cursor.getString(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Weather entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (!TextUtils.isEmpty(entity.getCode())) {
            values.put(COLUMN_CODE, entity.getCode());
        } else {
            values.putNull(COLUMN_CODE);
        }
        if (!TextUtils.isEmpty(entity.getDescription())) {
            values.put(COLUMN_DESCRIPTION, entity.getDescription());
        } else {
            values.putNull(COLUMN_DESCRIPTION);
        }
        if (!TextUtils.isEmpty(entity.getIcon())) {
            values.put(COLUMN_ICON, entity.getIcon());
        } else {
            values.putNull(COLUMN_ICON);
        }
        return values;
    }
}
