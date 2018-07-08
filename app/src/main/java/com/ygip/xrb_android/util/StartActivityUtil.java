package com.ygip.xrb_android.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ygip.xrb_android.App;

import java.io.Serializable;



/**
 * Created by lockyluo on 2017/7/29.
 * activity启动工具
 * 减少代码量
 */

public class StartActivityUtil {
    private static Intent intent;

//    public static void start(Context context, Class clazz) {
//        intent = new Intent(context, clazz);
//
//        if(App.androidLogAdapter==null){
//            App.androidLogAdapter=new AndroidLogAdapter(getFormatStrategy());
//            Logger.addLogAdapter(App.androidLogAdapter);
//        }
//        Logger.d("startActivity without data");
//        context.startActivity(intent);
//
//    }

//    public static void start(Context context, Class clazz, Serializable data) {
//        intent = new Intent(context, clazz);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", data);
//        intent.putExtras(bundle);
//
//        if(App.androidLogAdapter==null){
//            App.androidLogAdapter=new AndroidLogAdapter(getFormatStrategy());
//            Logger.addLogAdapter(App.androidLogAdapter);
//        }
//        Logger.d("startActivity with data");
//        context.startActivity(intent);
//    }

//    private static FormatStrategy getFormatStrategy(){
//        FormatStrategy formatStrategy= PrettyFormatStrategy
//                .newBuilder()
//                .tag("locky part").build();
//        return  formatStrategy;
//    }
}
