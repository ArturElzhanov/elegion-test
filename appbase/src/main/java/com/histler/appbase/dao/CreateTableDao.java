package com.histler.appbase.dao;

import android.database.sqlite.SQLiteDatabase;


public interface CreateTableDao {
    void onCreate(SQLiteDatabase database);

    void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion);
}
