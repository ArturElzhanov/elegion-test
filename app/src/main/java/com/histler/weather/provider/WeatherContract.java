package com.histler.weather.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class WeatherContract {
    /**
     * Content provider authority.
     */
    public static final String CONTENT_AUTHORITY = "com.histler.weather.syncadapter";
    /**
     * Base URI. (content://com.example.android.basicsyncadapter)
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Path component for "entry"-type resources..
     */
    private static final String PATH_WEATHER_INFOS = "weatherinfos";

    private WeatherContract() {
    }

    public static class WeatherInfo implements BaseColumns {
        /**
         * MIME type for lists of weather infos.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.syncadapter.weatherinfos";
        /**
         * MIME type for individual weather infos.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.syncadapter.weatherinfo";

        /**
         * Fully qualified URI for "weather info" resources.
         */
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_WEATHER_INFOS).build();

    /*    *//**
         * Table name where records are stored for "weather info" resources.
         *//*
        public static final String TABLE_NAME = "weather_info";
        *//**
         * Atom ID. (Note: Not to be confused with the database primary key, which is _ID.
         *//*
        public static final String COLUMN_NAME_ENTRY_ID = "entry_id";
        *//**
         * Article title
         *//*
        public static final String COLUMN_NAME_TITLE = "title";
        *//**
         * Article hyperlink. Corresponds to the rel="alternate" link in the
         * Atom spec.
         *//*
        public static final String COLUMN_NAME_LINK = "link";
        *//**
         * Date article was published.
         *//*
        public static final String COLUMN_NAME_PUBLISHED = "published";*/
    }
}
