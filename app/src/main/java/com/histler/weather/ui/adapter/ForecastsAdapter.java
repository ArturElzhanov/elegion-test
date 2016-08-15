package com.histler.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.histler.appbase.adapter.BaseRecyclerAdapter;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.forecast.Forecast;
import com.histler.weather.ui.adapter.viewholder.DailyViewHolder;

import java.util.List;

/**
 * Created by Badr
 * on 14.08.2016 4:36.
 */
public class ForecastsAdapter extends BaseRecyclerAdapter<Forecast, DailyViewHolder> {

    public ForecastsAdapter(List<Forecast> data) {
        super(data);
    }

    @Override
    public DailyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_row, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyViewHolder holder, int position) {
        Forecast entity = getItem(position);
        holder.initValue(entity);
    }
}
