package com.histler.appbase.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.histler.appbase.entity.IHasId;

import java.util.ArrayList;
import java.util.List;


public abstract class GeneralDao<E extends IHasId> implements CreateTableDao {
    public static final String COLUMN_ID = "_id";

    protected String getTableName() {
        return getClass().getSimpleName();
    }

    protected abstract String[] getAllColumns();

    public abstract String getNoTableNameCreateTableQuery();

    public abstract E cursorToEntity(Cursor cursor, int index);

    public String getOrderBy() {
        return null;
    }

    public long save(SQLiteDatabase database, E entity) {
        ContentValues values = entityToContentValues(entity);
        return database.insert(getTableName(), null, values);
    }

    public void saveAll(SQLiteDatabase database, List<E> entities) {
        for (E entity : entities) {
            save(database, entity);
        }
    }

    public int update(SQLiteDatabase database, E entity) {
        ContentValues values = entityToContentValues(entity);
        return database.update(getTableName(), values, COLUMN_ID + " =?", new String[]{String.valueOf(entity.getId())});
    }

    public void saveOrUpdate(SQLiteDatabase database, E entity) {
        E entityFromDB = getById(database, entity.getId());
        if (entityFromDB == null) {
            save(database, entity);
        } else {
            update(database, entity);
        }
    }

    public E getById(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(
                getTableName()
                , getAllColumns(), COLUMN_ID + " =?"
                , new String[]{String.valueOf(id)}, null, null, null
        );
        try {
            if (cursor.moveToFirst()) {
                return cursorToEntity(cursor, 0);
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    protected ContentValues entityToContentValues(E entity) {
        ContentValues values = new ContentValues();

        if (entity.getId() != 0) {
            values.put(COLUMN_ID, entity.getId());
        }
        return values;
    }

    public boolean delete(SQLiteDatabase database, E entity) {
        return database.delete(getTableName(), COLUMN_ID + "=?", new String[]{String.valueOf(entity.getId())}) > 0;
    }

    public boolean deleteAll(SQLiteDatabase database) {
        return database.delete(getTableName(), null, null) > 0;
    }

    public List<E> getAllEntities(SQLiteDatabase database) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), null, null, null, null, getOrderBy());
        try {
            List<E> entities = new ArrayList<>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    E entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("create table if not exists " + getTableName() + " " + getNoTableNameCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //do nothing for now
    }

}
