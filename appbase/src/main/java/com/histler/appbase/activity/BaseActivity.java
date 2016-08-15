package com.histler.appbase.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.histler.appbase.BaseBeanContainer;
import com.histler.appbase.R;
import com.histler.appbase.service.NavigationService;
import com.histler.appbase.util.Navigate;
import com.histler.appbase.util.permission.PermissionRequestListener;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends AppCompatActivity {
    private SparseArrayCompat<PermissionRequestListener> mPermissionsListeners = new SparseArrayCompat<>();
    private NavigationService mNavigationService = BaseBeanContainer.INSTANCE.getNavigationService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.containsKey(Navigate.PARAM_THEME)) {
            setTheme(bundle.getInt(Navigate.PARAM_THEME));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_base);
        initFragment(intent);
    }

    protected void initFragment() {
        initFragment(getIntent());
    }

    protected void initFragment(Intent intent) {
        String fragmentName;
        try {
            if (intent.hasExtra(Navigate.PARAM_CLASS)) {
                fragmentName = intent.getStringExtra(Navigate.PARAM_CLASS);
            } else {
                fragmentName = mNavigationService.getDefaultFragment().getName();
            }
            Fragment fragment = (Fragment) Class.forName(fragmentName).newInstance();
            fragment.setArguments(intent.getBundleExtra(Navigate.PARAM_ARGS));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragment, "main").commitAllowingStateLoss();
        } catch (Exception e) {
            Log.w(getClass().getName(), e.getMessage());
            finish();
        }
    }

    public void requestPermissions(PermissionRequestListener listener, String[] permissions, int requestCode) {
        List<String> granted = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionsListeners.put(requestCode, listener);
                ActivityCompat.requestPermissions(this, permissions, requestCode);
                break;
            } else {
                granted.add(permission);
            }
        }
        if (granted.size() > 0) {
            listener.onPermissionGranted(granted.toArray(new String[granted.size()]));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionRequestListener listener = mPermissionsListeners.get(requestCode);
        if (listener != null) {
            List<String> grantedPermissions = new ArrayList<>();
            for (int i = 0, size = grantResults.length; i < size; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(permissions[i]);
                }
            }
            listener.onPermissionGranted(grantedPermissions.toArray(new String[grantedPermissions.size()]));
        }
    }
}
