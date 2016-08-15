package com.histler.appbase;


import com.histler.appbase.dao.CreateTableDao;
import com.histler.appbase.service.NavigationService;

import java.util.ArrayList;
import java.util.List;


public enum BaseBeanContainer {
    INSTANCE;
    private final List<CreateTableDao> mAllDaos = new ArrayList<>();
    private NavigationService mNavigationService;

    public NavigationService getNavigationService() {
        return mNavigationService;
    }

    public void setNavigationService(NavigationService navigationService) {
        mNavigationService = navigationService;
    }

    public List<CreateTableDao> getAllDaos() {
        return mAllDaos;
    }
}
