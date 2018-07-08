package com.ygip.xrb_android.net;

import com.ygip.xrb_android.AppConfig;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by XQM on 2017/9/17.
 */

public class HttpMethod {
    private static Api api;
    public static Api getApi(){
        if (api == null){
            synchronized (HttpMethod.class){
                if (api == null){
                    //进行网络通信，创建Api接口对象
                    api = XApi.getInstance().getRetrofit(AppConfig.baseUrl,true).create(Api.class);
                }
            }
        }
        return api;
    }

}
