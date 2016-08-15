package com.histler.appbase.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;


public class DatabaseManager {
    private static volatile DatabaseManager sInstance;
    private static SQLiteOpenHelper sDatabaseHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    public static DatabaseManager getInstance(Context context) {
        DatabaseManager localInstance = sInstance;
        if (localInstance == null) {
            synchronized (DatabaseManager.class) {
                localInstance = sInstance;
                if (localInstance == null) {
                    sInstance = localInstance = new DatabaseManager();
                    sDatabaseHelper = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return localInstance;
    }

    public synchronized SQLiteDatabase openWritableDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = sDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }

}
