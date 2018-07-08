package com.ygip.xrb_android.mvp.login.present;

import com.ygip.xrb_android.mvp.login.view.LoginActivity;
import com.ygip.xrb_android.net.HttpMethod;
import com.ygip.xrb_android.net.HttpResult;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by XQM on 2017/7/22.
 */

public class LoginPresent extends XPresent<LoginActivity> {

    public void getUserLoginInfo(String memberName,String password,String phoneNumber,String department){
        Map<String,String> map = new HashMap<>();
        map.put("memberName",memberName);
        map.put("password",password);
        map.put("phoneNumber",phoneNumber);
        map.put("department",department);

        HttpMethod.getApi().getUserLoginInfo(map)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(getV().bindToLifecycle())
                .subscribe(new ApiSubscriber<HttpResult<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        log(error.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<String> stringHttpResult) {
                        log(stringHttpResult.toString());
                    }
                });
    }
}
