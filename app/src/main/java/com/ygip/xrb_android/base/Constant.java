package com.ygip.xrb_android.base;

/**
 * 存放常量
 * Created by XQM on 2017/8/30.
 */

public interface Constant {
    String saveFolder = "/Pic";
    int MAX_PICS = 10;

    String httpCachePath = saveFolder + "/httpCache";	// 网络缓存路径
    String imgCachePath = saveFolder + "/imageCache";	// 图片缓存路径
    String maintenancePicPath = saveFolder + "/maintenancePicPath"; // 维保压缩后的图片保存路径

    String KEY_PICS = "pics";
}
