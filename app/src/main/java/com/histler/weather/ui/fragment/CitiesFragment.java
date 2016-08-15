package com.histler.weather.ui.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.histler.appbase.adapter.OnItemClickListener;
import com.histler.appbase.fragment.BaseRecyclerFragment;
import com.histler.appbase.util.Navigate;
import com.histler.appbase.util.NetworkUtils;
import com.histler.appbase.util.UiUtils;
import com.histler.appbase.util.robospice.LocalSpiceService;
import com.histler.weather.R;
import com.histler.weather.remote.api.openweather.daily.CityDailyWeather;
import com.histler.weather.remote.api.openweather.forecast.City;
import com.histler.weather.task.CitiesDailyWeatherRequest;
import com.histler.weather.task.CitySearchRequest;
import com.histler.weather.task.LocalCitiesDailyWeatherRequest;
import com.histler.weather.task.SaveCitySearchRequest;
import com.histler.weather.ui.adapter.CitiesAdapter;
import com.histler.weather.ui.adapter.viewholder.CityViewHolder;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by Badr
 * on 13.08.2016 20:17.
 */
public class CitiesFragment extends BaseRecyclerFragment<CityDailyWeather, CityViewHolder> implements View.OnClickListener {
    private final SpiceManager mSpiceManager = new SpiceManager(LocalSpiceService.class);
    @Bind(R.id.search_holder)
    View mSearchHolder;
    @Bind(R.id.search_view)
    EditText mSearchView;
    @Bind(R.id.search_recycler)
    RecyclerView mSearchRecyclerView;
    @Bind(R.id.search_progressbar)
    View mSearchProgress;
    private CitiesDailyWeatherListener mCitiesDailyWeatherListener = new CitiesDailyWeatherListener();
    private CitiesSearchListener mCitiesSearchListener = new CitiesSearchListener();

    protected SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    @Override
    public void onStart() {
        if (!getSpiceManager().isStarted()) {
            getSpiceManager().start(getActivity().getApplicationContext());
            onRefresh();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (getSpiceManager().isStarted()) {
            getSpiceManager().shouldStop();
        }
        super.onDestroy();
    }

    public void onHardRefresh() {
        setHardRefreshing();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        Context context = getContext();
        if (NetworkUtils.isNetworkAvailable(context)) {
            loadExternal(context);
        } else {
            loadLocal(context);
        }
    }

    private void loadLocal(Context context) {
        getSpiceManager().execute(new LocalCitiesDailyWeatherRequest(context), mCitiesDailyWeatherListener);
    }

    private void loadExternal(Context context) {
        getSpiceManager().execute(new CitiesDailyWeatherRequest(context), mCitiesDailyWeatherListener);
    }
    @Override
    public boolean isNeedDivider() {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getEmptyView().findViewById(R.id.empty_btn).setOnClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                toggleSearchToolbar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    UiUtils.hideKeyboard(getActivity());
                    onSearchTextEntered(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void onSearchTextEntered(String searchQuery) {
        mSearchRecyclerView.setVisibility(View.GONE);
        mSearchProgress.setVisibility(View.VISIBLE);
        getSpiceManager().execute(new CitySearchRequest(searchQuery), mCitiesSearchListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cities_list;
    }

    @OnClick(R.id.search_close)
    protected void toggleSearchToolbar() {
        Activity activity = getActivity();
        UiUtils.hideKeyboard(activity);
        int cx = mSearchHolder.getRight();
        int cy = mSearchHolder.getTop();
        float radius = Math.max(mSearchHolder.getWidth(), mSearchHolder.getHeight()) * 2.0f;

        // Android native animator
        Animator animator;
        if (mSearchHolder.getVisibility() == View.INVISIBLE) {
            mSearchHolder.setVisibility(View.VISIBLE);
            animator = ViewAnimationUtils.createCircularReveal(mSearchHolder, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateInterpolator(2f));
        } else {
            animator = ViewAnimationUtils.createCircularReveal(mSearchHolder, cx, cy, radius, 0);

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchHolder.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animator.setInterpolator(new DecelerateInterpolator(2f));
        }
        animator.setDuration(600);
        animator.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.empty_btn) {
            onHardRefresh();
        }
    }

    @Override
    public void onRecyclerViewItemClick(View v, int position) {
        CityDailyWeather entity = getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        City city = new City();
        city.setId(entity.getId());
        city.setName(entity.getCityName());
        city.setCountry(entity.getCountryName());
        city.setCoordinate(entity.getCoordinate());
        bundle.putSerializable(Navigate.PARAM_ENTITY, city);
        Navigate.to(getContext(), CityWeatherFragment.class, bundle);
    }

    @Override
    protected String getTitle() {
        return getString(R.string.cities);
    }

    private class CitiesDailyWeatherListener implements RequestListener<CityDailyWeather.List> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (isAdded()) {
                setRefreshed();
                showMessage(getString(R.string.request_with_error), getString(R.string.repeat), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onHardRefresh();
                    }
                });
            }
        }

        @Override
        public void onRequestSuccess(CityDailyWeather.List list) {
            if (isAdded()) {
                setRefreshed();
                setAdapter(new CitiesAdapter(list));
            }
        }
    }

    private class CitiesSearchListener implements RequestListener<CityDailyWeather.List>, OnItemClickListener {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            if (isAdded()) {
                mSearchRecyclerView.setVisibility(View.VISIBLE);
                mSearchProgress.setVisibility(View.GONE);
                Toast.makeText(getContext(), R.string.error_loading_cities, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestSuccess(CityDailyWeather.List cityDailyWeathers) {
            if (isAdded()) {
                mSearchRecyclerView.setVisibility(View.VISIBLE);
                mSearchProgress.setVisibility(View.GONE);
                CitiesAdapter adapter = new CitiesAdapter(cityDailyWeathers);
                adapter.setOnItemClickListener(this);
                mSearchRecyclerView.setAdapter(adapter);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            CityDailyWeather searchClicked = ((CitiesAdapter) mSearchRecyclerView.getAdapter()).getItem(position);
            getSpiceManager().execute(new SaveCitySearchRequest(getContext(), searchClicked), new CitySavesListener());
            toggleSearchToolbar();
        }
    }

    private class CitySavesListener implements RequestListener<CityDailyWeather> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(CityDailyWeather entity) {
            if (isAdded()) {
                loadLocal(getContext());
            }
        }
    }
}
