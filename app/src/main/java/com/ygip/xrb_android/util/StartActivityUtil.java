package com.ygip.xrb_android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ygip.xrb_android.App;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created by lockyluo on 2017/7/29.
 * activity启动工具
 * 减少代码量
 */

public class StartActivityUtil {
//    private static Intent intent;

    public static void startActivity(Activity activity,
                                           Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
//        activity.finish();
    }

    public static void startActivityWithData(Activity activity,
                                           Class<? extends Activity> cls,
                                           HashMap<String, ? extends Object> hashMap) {
        Intent intent = new Intent(activity, cls);
        Iterator<?> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            @SuppressWarnings("unchecked")
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                    .next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                intent.putExtra(key, (String) value);
            }
            if (value instanceof Boolean) {
                intent.putExtra(key, (boolean) value);
            }
            if (value instanceof Integer) {
                intent.putExtra(key, (int) value);
            }
            if (value instanceof Float) {
                intent.putExtra(key, (float) value);
            }
            if (value instanceof Double) {
                intent.putExtra(key, (double) value);
            }
        }
        activity.startActivity(intent);
    }


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
