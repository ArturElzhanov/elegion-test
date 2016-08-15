package com.histler.weather;


import com.histler.appbase.BaseBeanContainer;
import com.histler.weather.service.NavigationServiceImpl;


public final class Initializer {
    protected static void initialize() {
        BaseBeanContainer baseBeanContainer = BaseBeanContainer.INSTANCE;
        baseBeanContainer.setNavigationService(new NavigationServiceImpl());

        AppBeanContainer appBeanContainer = AppBeanContainer.INSTANCE;
        baseBeanContainer.getAllDaos().addAll(appBeanContainer.getAllDaos());
    }
}
