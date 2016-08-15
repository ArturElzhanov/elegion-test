package com.histler.appbase.service;

import android.support.v4.app.Fragment;

import com.histler.appbase.activity.BaseActivity;



public interface NavigationService {
    Class<? extends BaseActivity> getMainActivityClass();

    Class<? extends BaseActivity> getActivityClass();

    Class<? extends Fragment> getDefaultFragment();
}
