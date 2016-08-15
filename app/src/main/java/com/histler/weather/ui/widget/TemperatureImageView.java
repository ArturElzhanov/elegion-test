package com.histler.weather.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.histler.common.util.DateUtils;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.Constants;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by Badr
 * on 13.08.2016 18:32.
 */
public class TemperatureImageView extends FrameLayout {

    private TextView mDayOfWeek;
    private ImageView mWeatherImage;
    private TextView mTempValue;

    public TemperatureImageView(Context context) {
        this(context, null);
    }

    public TemperatureImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TemperatureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (!isInEditMode()) {
            inflate(context, R.layout.temp_icon_layout, this);
            mDayOfWeek = (TextView) findViewById(R.id.day_of_week);
            mWeatherImage = (ImageView) findViewById(R.id.weather_image);
            mTempValue = (TextView) findViewById(R.id.temperature_value);

        }
    }

    public String getDayOfWeek() {
        return mDayOfWeek.getText().toString();
    }

    public void setDayOfWeek(Date date) {
        if (date != null) {
            mDayOfWeek.setText(DateUtils.DAY_OF_WEEK_DATE_FORMAT.format(date));
        } else {
            mDayOfWeek.setText("");
            mDayOfWeek.setVisibility(GONE);
        }
    }

    public void setWeatherImage(String iconId) {
        if (iconId != null) {
            Glide
                    .with(getContext())
                    .load(MessageFormat.format(Constants.ICON_TEMPLATE_URL, iconId))
                    .into(mWeatherImage);
        }
    }

    //todo by default we think, that this is celsius temp value. make it work with kelvin and fahrenheit too
    public void setCelsiusTemperature(Float temperature) {
        if (temperature != null) {
            mTempValue.setVisibility(VISIBLE);
            int shownTemp = Math.round(temperature);
            mTempValue.setText(MessageFormat.format("{0}°C", shownTemp));
        } else {
            mTempValue.setVisibility(GONE);
        }
    }

    public void setCelsiusMinMaxTemperature(float maxTemp, float minTemp) {
        int shownMinTemp = Math.round(minTemp);
        int shownMaxTemp = Math.round(maxTemp);
        mTempValue.setText(MessageFormat.format(getContext().getString(R.string.max_min_temp), shownMinTemp, shownMaxTemp));
    }
}
