package com.ygip.xrb_android.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by lockyluo on 2017/7/30.
 * 振动
 */

public class MyVibrator {
    private static Vibrator vibrator;
    private static final long defaultTime=170L;

    public static void vibrate(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(defaultTime);
    }
    public static void vibrate(Context context, long time) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }
}
