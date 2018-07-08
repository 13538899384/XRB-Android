package com.ygip.xrb_android.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PowerManager;
import android.util.Log;

import java.util.List;

/**
 * ClassName: CommonUtils
 *
 * @author kesar
 * @Description: 常用控件
 * @date 2015-11-12
 */
public class CommonUtils {
    /**
     * @Description: 检测网络是否可用
     * @param @param context
     * @param @return
     * @return boolean
     * @throws
     * @author kesar
     * @date 2015-11-12
     */
    private static final String TAG = "CommonUtils";

    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param @return
     * @return boolean
     * @throws
     * @Description: 检测Sdcard是否存在
     * @author kesar
     * @date 2015-11-12
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param @param  context
     * @param @return true 在后台 false 在前台
     * @return boolean
     * @throws
     * @Description: 判断应用是否在后台
     * @author kesar
     * @date 2015-12-10
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i(TAG, "后台");
                    return true;
                } else {
                    Log.i(TAG, "前台");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @param @param  context
     * @param @return true 没有熄屏，false 熄屏
     * @return boolean
     * @throws
     * @Description: 判断手机是否熄屏 (api 14以上)
     * @author kesar
     * @date 2015-12-10
     */
    public static boolean isScreenOn(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();
        if (isScreenOn) {
            Log.i(TAG, "没熄屏");
        } else {
            Log.i(TAG, "熄屏");
        }
        return isScreenOn;
    }
}
