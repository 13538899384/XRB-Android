package com.ygip.xrb_android.net;

import java.io.IOException;

import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.NetProvider;
import cn.droidlover.xdroidmvp.net.RequestHandler;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加请求头
 * Created by XQM on 2017/9/17.
 */

public class NetPropare implements NetProvider {
    @Override
    public Interceptor[] configInterceptors() {
        Interceptor[] interceptors = new Interceptor[1];
        interceptors[0] = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();

                Request newRequest = oldRequest.newBuilder()
                        .addHeader("Content-Type","application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };
        return interceptors;
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return null;
    }

    @Override
    public RequestHandler configHandler() {
        return null;
    }

    @Override
    public long configConnectTimeoutMills() {
        return 20000;
    }

    @Override
    public long configReadTimeoutMills() {
        return 20000;
    }

    @Override
    public boolean configLogEnable() {
        return true;
    }

    @Override
    public boolean handleError(NetError error) {
        return false;
    }
}
