package com.histler.weather.remote.api.openweather;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public abstract class OpenWeatherResult<DATA, MESSAGE> implements Serializable {
    @SerializedName("cod")
    private String resultCode;
    private MESSAGE message;

    private List<DATA> list;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public MESSAGE getMessage() {
        return message;
    }

    public void setMessage(MESSAGE message) {
        this.message = message;
    }

    public List<DATA> getList() {
        return list;
    }

    public void setList(List<DATA> list) {
        this.list = list;
    }
}
