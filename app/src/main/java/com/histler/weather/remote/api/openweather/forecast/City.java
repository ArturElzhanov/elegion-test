package com.histler.weather.remote.api.openweather.forecast;

import com.google.gson.annotations.SerializedName;
import com.histler.appbase.entity.IHasId;
import com.histler.weather.remote.api.openweather.Coordinate;

import java.io.Serializable;


public class City implements Serializable, IHasId {
    private long id;
    private String name;
    @SerializedName("coord")
    private Coordinate coordinate;
    private String country;
    private long population;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

}
