package com.histler.weather.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.histler.appbase.adapter.OnItemClickListener;
import com.histler.appbase.adapter.viewholder.BaseViewHolder;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.Weather;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.ui.widget.TemperatureImageView;

import butterknife.Bind;

/**
 * Created by Badr
 * on 13.08.2016 18:09.
 */
public class CityViewHolder extends BaseViewHolder {

    @Bind(R.id.city)
    TextView cityNameView;
    @Bind(R.id.country)
    TextView countryNameView;
    @Bind(R.id.temp_value)
    TemperatureImageView tempValueView;

    public CityViewHolder(View itemView, OnItemClickListener clickListener) {
        super(itemView, clickListener);
    }

    public void initValue(City city) {
        cityNameView.setText(city.getName());
        countryNameView.setText(city.getCountry());
        tempValueView.setVisibility(View.GONE);
    }

    public void initValue(CityDailyWeather cityDailyWeather) {
        cityNameView.setText(cityDailyWeather.getCityName());
        countryNameView.setText(cityDailyWeather.getCountryName());

        Weather first = cityDailyWeather.getWeathers() != null && !cityDailyWeather.getWeathers().isEmpty() ? cityDailyWeather.getWeathers().get(0) : null;
        if (first != null) {
            tempValueView.setVisibility(View.VISIBLE);
            tempValueView.setDayOfWeek(null/*cityDailyWeather.getDate()*/);
            tempValueView.setWeatherImage(first.getIcon());
            if (cityDailyWeather.getTemperature() != null) {
                tempValueView.setCelsiusMinMaxTemperature(cityDailyWeather.getTemperature().getMaxTemperature(), cityDailyWeather.getTemperature().getMinTemperature());
            } else {
                tempValueView.setCelsiusTemperature(null);
            }
        } else {
            tempValueView.setVisibility(View.GONE);
        }
    }
}
