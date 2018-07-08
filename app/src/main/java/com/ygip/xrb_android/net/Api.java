package com.ygip.xrb_android.net;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by XQM on 2017/9/17.
 */

public interface Api {

    @POST("base/api/user")
    Flowable<HttpResult<String>> getUserLoginInfo(@Body Map<String,String> map);
}
