package com.histler.weather.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.histler.appbase.adapter.BaseRecyclerAdapter;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.ui.adapter.viewholder.CityViewHolder;

import java.util.List;

/**
 * Created by Badr
 * on 13.08.2016 18:04.
 */
public class CitiesAdapter extends BaseRecyclerAdapter<CityDailyWeather, CityViewHolder> {
    private static final int HEADER_VIEW_TYPE = 0;
    private static final int CITY_VIEW_TYPE = 1;

    public CitiesAdapter(List<CityDailyWeather> data) {
        super(data);
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.city_row, parent, false);
        return new CityViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        CityDailyWeather item = getItem(position);
        /*if (item.isHeader) {
            HeaderViewHolder hvh = (HeaderViewHolder) holder;
            hvh.initValue((String) item.data);
        } else {
        }*/
        holder.initValue(item);
    }
}