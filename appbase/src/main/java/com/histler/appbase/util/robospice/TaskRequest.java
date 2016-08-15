package com.histler.appbase.util.robospice;

import com.octo.android.robospice.request.SpiceRequest;

/**
 * Created by Badr
 * on 29.05.2016 1:29.
 */
public abstract class TaskRequest<T> extends SpiceRequest<T> {
    public TaskRequest(Class<T> clazz) {
        super(clazz);
    }

    public abstract T loadData() throws Exception;

    @Override
    public T loadDataFromNetwork() throws Exception {
        return loadData();
    }
}
