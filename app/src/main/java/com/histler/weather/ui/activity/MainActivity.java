package com.histler.weather.ui.activity;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.histler.appbase.util.BackListener;
import com.histler.weather.R;
import com.octo.android.robospice.persistence.DurationInMillis;

public class MainActivity extends FragmentWrapperActivity {
    private boolean mDoubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (
                currentFragment == null
                        || !(currentFragment instanceof BackListener)
                        || !((BackListener) currentFragment).onBackPressed()) {
            if (mDoubleBackToExitPressedOnce) {
                finish();
            } else {
                mDoubleBackToExitPressedOnce = true;
                Toast.makeText(this, getString(R.string.back_toast), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mDoubleBackToExitPressedOnce = false;
                    }
                }, DurationInMillis.ONE_SECOND * 2);
            }
        }
    }
}
