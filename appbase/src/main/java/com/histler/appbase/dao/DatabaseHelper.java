package com.histler.appbase.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.histler.appbase.BaseBeanContainer;

import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "base.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<CreateTableDao> allDaos = BaseBeanContainer.INSTANCE.getAllDaos();
        for (CreateTableDao dao : allDaos) {
            dao.onCreate(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<CreateTableDao> allDaos = BaseBeanContainer.INSTANCE.getAllDaos();
        for (CreateTableDao dao : allDaos) {
            dao.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
