package com.ygip.xrb_android;

import android.support.multidex.MultiDexApplication;

import com.ygip.xrb_android.helper.CrashModel;
import com.ygip.xrb_android.helper.UncaughtExceptionHandlerHelper;

/**
 * Created by XQM on 2017/7/14.
 */

public class App extends MultiDexApplication {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //弹出崩溃信息展示界面
        UncaughtExceptionHandlerHelper.getInstance()
                .init(this)
                //设置是否捕获异常，不弹出崩溃框
                .setEnable(true)
                //设置是否显示崩溃信息展示页面
                .showCrashMessage(true)
                //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到,
                .setOnCrashListener(new UncaughtExceptionHandlerHelper.OnCrashListener() {
                    @Override
                    public void onCrash(Thread t, Throwable ex, CrashModel model) {
                        //CrashModel 崩溃信息记录，包含设备信息
                    }
                });
    }


    public static App getInstance(){
        return instance;
    }
}
