package com.histler.weather.ui.adapter.viewholder;

import android.view.View;

import com.histler.appbase.adapter.viewholder.BaseViewHolder;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.Weather;
import com.histler.weather.remote.api.openweather.forecast.Forecast;
import com.histler.weather.ui.widget.TemperatureImageView;

import butterknife.Bind;

/**
 * Created by Badr
 * on 14.08.2016 4:32.
 */
public class DailyViewHolder extends BaseViewHolder {

    @Bind(R.id.temp_value)
    TemperatureImageView tempValueView;

    public DailyViewHolder(View itemView) {
        super(itemView);
    }

    public void initValue(Forecast forecast) {
        Weather first = forecast.getWeathers() != null && !forecast.getWeathers().isEmpty() ? forecast.getWeathers().get(0) : null;
        if (first != null) {
            tempValueView.setVisibility(View.VISIBLE);
            tempValueView.setDayOfWeek(forecast.getDate());
            tempValueView.setWeatherImage(first.getIcon());
            if (forecast.getTemperature() != null) {
                tempValueView.setCelsiusMinMaxTemperature(forecast.getTemperature().getMax(), forecast.getTemperature().getMin());
            } else {
                tempValueView.setCelsiusTemperature(null);
            }
        } else {
            tempValueView.setVisibility(View.GONE);
        }
    }
}
