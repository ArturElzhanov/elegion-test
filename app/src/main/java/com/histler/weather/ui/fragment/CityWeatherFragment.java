package com.histler.weather.ui.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.histler.appbase.fragment.BaseFragment;
import com.histler.appbase.util.Navigate;
import com.histler.appbase.util.NetworkUtils;
import com.histler.appbase.util.robospice.LocalSpiceService;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.daily.DailyTemperature;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.remote.api.openweather.forecast.Forecast;
import com.histler.weather.remote.api.openweather.forecast.Temperature;
import com.histler.weather.task.CityDailyWeatherRequest;
import com.histler.weather.task.CityForecastRequest;
import com.histler.weather.task.LocalCityForecastRequest;
import com.histler.weather.ui.adapter.ForecastsAdapter;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.text.MessageFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Badr
 * on 14.08.2016 3:42.
 */
public class CityWeatherFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private final SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    @Bind(R.id.listContainer)
    SwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.temp_current)
    TextView mCurrentTemp;
    @Bind(R.id.temp_max_min)
    TextView mMaxMinTemp;
    @Bind(R.id.weather)
    TextView mWeatherDesc;
    @Bind(R.id.pressure)
    TextView mPressure;
    @Bind(R.id.humidity)
    TextView mHumidity;
    @Bind(R.id.forecast_recycler)
    RecyclerView mForecastRecyclerView;
    @Bind(R.id.progressbar)
    View mProgressBar;
    private City mCity;
    private boolean mIsExternalLoaded = false;
    private ExternalLoadListener mExLoadListener = new ExternalLoadListener();
    private ExternalCurrentListener mExternalCurrentTempListener = new ExternalCurrentListener();

    protected SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    @Override
    public void onStart() {
        if (!getSpiceManager().isStarted()) {
            getSpiceManager().start(getActivity().getApplicationContext());
            onLoadLocal();
            if (NetworkUtils.isNetworkAvailable(getContext())) {
                onLoadExternal();
            }
        }
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCity = (City) getArguments().get(Navigate.PARAM_ENTITY);
    }

    private void onLoadExternal() {
        getSpiceManager().execute(new CityForecastRequest(getContext(), mCity), mExLoadListener);
        getSpiceManager().execute(new CityDailyWeatherRequest(getContext(), mCity), mExternalCurrentTempListener);
    }

    private void onLoadLocal() {
        getSpiceManager().execute(new LocalCityForecastRequest(getContext(), mCity), new LocalLoadListener());
    }

    @Override
    public void onDestroy() {
        if (getSpiceManager().isStarted()) {
            getSpiceManager().shouldStop();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.city_weather_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initListContainer(view);
        initList();
    }

    private void initList() {
        mForecastRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mForecastRecyclerView.setLayoutManager(layoutManager);
    }

    private void initListContainer(View view) {
        mSwipeRefresh.setOnRefreshListener(this);
        TypedValue typedValue = new TypedValue();
        TypedArray a = view.getContext().obtainStyledAttributes(typedValue.data, new int[]{com.histler.appbase.R.attr.colorAccent});
        int colorAccent = a.getColor(0, 0);
        a.recycle();
        mSwipeRefresh.setColorSchemeColors(colorAccent);
    }

    @Override
    protected String getTitle() {
        return mCity != null ? mCity.getName() : null;
    }

    @Override
    protected String getSubTitle() {
        return mCity != null ? mCity.getCountry() : null;
    }

    private void initForecasts(Forecast.List forecasts) {
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefresh.setVisibility(View.VISIBLE);
        mSwipeRefresh.setRefreshing(false);
        mForecastRecyclerView.setAdapter(new ForecastsAdapter(forecasts));
        if (forecasts != null && !forecasts.isEmpty()) {
            Forecast first = forecasts.get(0);
            Temperature temperature = first.getTemperature();
            mMaxMinTemp.setText(MessageFormat.format(getString(R.string.max_min_temp), Math.round(temperature.getMax()), Math.round(temperature.getMin())));
            mHumidity.setText(MessageFormat.format(getString(R.string.humidity), first.getHumidity()));
            mPressure.setText(MessageFormat.format(getString(R.string.pressure), Math.round(first.getPressure())));
            if (first.getWeathers() != null && !first.getWeathers().isEmpty()) {
                mWeatherDesc.setText(first.getWeathers().get(0).getDescription());
            }
        }
    }

    private void initDailyWeather(CityDailyWeather cityDailyWeather) {
        DailyTemperature temp = cityDailyWeather.getTemperature();
        if (temp != null) {
            mCurrentTemp.setText(MessageFormat.format(getString(R.string.temp), Math.round(temp.getTemperature())));
        }
    }

    @Override
    public void onRefresh() {
        onLoadExternal();
    }

    private class LocalLoadListener implements RequestListener<Forecast.List> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(Forecast.List forecasts) {
            if (isAdded() && !mIsExternalLoaded) {
                initForecasts(forecasts);
            }
        }
    }

    private class ExternalCurrentListener implements RequestListener<CityDailyWeather> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //
        }

        @Override
        public void onRequestSuccess(CityDailyWeather cityDailyWeather) {
            if (isAdded()) {
                initDailyWeather(cityDailyWeather);
            }
        }
    }

    private class ExternalLoadListener implements RequestListener<Forecast.List> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            mIsExternalLoaded = true;
            if (isAdded()) {
                showMessage(getString(R.string.loading_forecast_error));
            }
        }

        @Override
        public void onRequestSuccess(Forecast.List forecasts) {
            mIsExternalLoaded = true;
            if (isAdded()) {
                initForecasts(forecasts);
            }
        }
    }
}
