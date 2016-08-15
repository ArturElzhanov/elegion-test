package com.histler.weather.service;

import android.support.v4.app.Fragment;

import com.histler.appbase.activity.BaseActivity;
import com.histler.appbase.service.NavigationService;
import com.histler.weather.ui.activity.FragmentWrapperActivity;
import com.histler.weather.ui.activity.MainActivity;
import com.histler.weather.ui.fragment.CitiesFragment;



public class NavigationServiceImpl implements NavigationService {
    @Override
    public Class<? extends BaseActivity> getMainActivityClass() {
        return MainActivity.class;
    }

    @Override
    public Class<? extends BaseActivity> getActivityClass() {
        return FragmentWrapperActivity.class;
    }

    @Override
    public Class<? extends Fragment> getDefaultFragment() {
        return CitiesFragment.class;
    }
}
