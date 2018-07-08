package com.ygip.xrb_android.net;

import cn.droidlover.xdroidmvp.net.IModel;

/**
 * Created by XQM on 2017/9/17.
 */

public class HttpResult<T> implements IModel {

    private String Msg;
    private int Code;
    private String Error;
    private boolean Success;
    private T Data;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }


    @Override
    public String toString() {
        return "HttpResult{" +
                "Msg='" + Msg + '\'' +
                ", Code=" + Code +
                ", Error='" + Error + '\'' +
                ", Success=" + Success +
                ", Data=" + Data +
                '}';
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
