package com.histler.appbase.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.histler.appbase.BaseBeanContainer;
import com.histler.appbase.service.NavigationService;


/**
 * Created by Badr
 * on 26.05.2016 18:14
 */
public final class Navigate {
    public static final String PARAM_CLASS = "class";
    public static final String PARAM_ARGS = "args";
    public static final String PARAM_HAS_MENU = "has_menu";
    public static final String PARAM_THEME = "theme";
    public static final String PARAM_ID = "id";
    public static final String PARAM_ENTITY = "entity";


    public static void to(Context context, Class fragmentClass, Bundle bundle) {
        NavigationService navigationService = BaseBeanContainer.INSTANCE.getNavigationService();
        Intent intent = new Intent(context, navigationService.getActivityClass());
        intent.putExtra(PARAM_CLASS, fragmentClass.getName());
        if (bundle == null) {
            bundle = new Bundle();
        }
        intent.putExtra(PARAM_ARGS, bundle);
        context.startActivity(intent);
    }
}
