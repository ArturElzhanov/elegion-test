package com.histler.weather.remote.api.openweather;

import com.google.gson.annotations.SerializedName;
import com.histler.appbase.entity.IHasId;

import java.io.Serializable;


public class Weather implements Serializable, IHasId {
    private long id;
    @SerializedName("main")
    private String code;
    private String description;
    private String icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
