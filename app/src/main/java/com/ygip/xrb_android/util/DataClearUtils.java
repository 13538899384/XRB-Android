package com.ygip.xrb_android.util;

import android.content.Context;

import java.io.File;

/**
 * 清理缓存工具类
 * Created by XQM on 2017/6/15.
 */

public class DataClearUtils {
    /**
     * 清理应用内部缓存
     * @param context
     */
    public static void cleanInternalCache(Context context){
        deleteFilesByDirectory(context.getCacheDir());
    }

    /**
     * 清理数据库缓存
     * @param context
     */
    public static void cleanDatabase(Context context){
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 清理SharePreference缓存
     * @param context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() +"/shared_prefs"));
    }

    /***
     * 清理file
     * @param context
     */
    public static void cleanFiles(Context context) {
        deleteFilesByDirectory(context.getFilesDir());
    }

    /**
     * 清除自定义路径下的文件，只支持目录下的文件删除
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFilesByDirectory(new File(filePath));
    }

    /**
     * 删除文件
     * @param file
     */
    private static void deleteFilesByDirectory(File file) {
        if (file != null && file.exists() && file.isDirectory()){
            for (File item:file.listFiles()){
                item.delete();
            }
        }
    }

    /**
     * 清除所有APP所有的缓存数据
     * @param context
     */
    public static void cleanApplicationData(Context context){
        cleanInternalCache(context);
        cleanDatabase(context);
        cleanSharedPreference(context);
        cleanFiles(context);
    }
}
