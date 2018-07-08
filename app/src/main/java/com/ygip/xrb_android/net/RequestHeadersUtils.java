package com.ygip.xrb_android.net;

import android.util.Log;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import cn.droidlover.xdroidmvp.kit.Codec;

/**
 * 生成网络请求头
 * Created by XQM on 2017/9/17.
 */

public class RequestHeadersUtils {

    private static final String TAG = "RequestHeadersUtils";

    private static int min = 1;
    private static int max = 10000;


    /**
     * 生成 key1+value1+key2+value2+ ... 的字符串
     * @param map
     * @return
     */
    public static String generateRequestMultiKeyValueStrs(Map<String, String> map){
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder.append(entry.getKey());
            builder.append(entry.getValue());
        }
        return builder.toString();
    }

    /**
     * 用于生成签名
     * @param map
     * @param appKey
     * @param appSecret
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String generateSignature(Map<String, String> map, String appKey, String appSecret, String timestamp, String nonce) {
        StringBuilder stringBuilder = new StringBuilder();
        if (map != null){
            stringBuilder.append(generateRequestMultiKeyValueStrs(map));
        }
        stringBuilder.append(appKey).append(appSecret).append(timestamp).append(nonce);
        char[] chars = stringBuilder.toString().toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        String signature = md5(sorted).toUpperCase();
        return signature;
    }

    public static String md5(String str){
        return Codec.MD5.getMessageDigest(str.getBytes());
    }

    public static String getRandom(){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min + "";
    }

    public static String getDate() {
        return new Date().getTime()/1000+"";
    }

    private static void log(String msg){
        Log.i(TAG, msg);
    }
}
