package com.ygip.xrb_android;

import android.support.multidex.MultiDexApplication;

/**
 * Created by XQM on 2017/7/14.
 */

public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public static App getInstance(){
        return instance;
    }
}
