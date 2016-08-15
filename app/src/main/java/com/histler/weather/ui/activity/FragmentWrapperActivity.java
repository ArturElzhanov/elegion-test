package com.histler.weather.ui.activity;

import android.support.v4.app.Fragment;

import com.histler.appbase.activity.BaseActivity;
import com.histler.appbase.util.BackListener;
import com.histler.weather.R;


/**
 * Created by Badr
 * on 28.05.2016.
 */
public class FragmentWrapperActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (
                currentFragment == null
                        || !(currentFragment instanceof BackListener)
                        || !((BackListener) currentFragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}
