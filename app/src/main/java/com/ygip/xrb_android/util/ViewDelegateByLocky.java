package com.ygip.xrb_android.util;

import android.content.Context;
import android.view.View;

import cn.droidlover.xdroidmvp.mvp.VDelegate;

/**
 * Created by lockyluo on 2017/8/3.
 */

public class ViewDelegateByLocky implements VDelegate {
    public Context getContext() {
        return context;
    }

    private Context context;

    private ViewDelegateByLocky(Context context) {
        this.context = context;
    }

    public static ViewDelegateByLocky create(Context context) {
        return new ViewDelegateByLocky(context);
    }

    public void onDialogResult(int pos){

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void visible(boolean flag, View view) {
        if (flag) view.setVisibility(View.VISIBLE);
    }

    @Override
    public void gone(boolean flag, View view) {
        if (flag) view.setVisibility(View.GONE);
    }

    @Override
    public void inVisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void toastShort(String msg) {
        ToastUtils.show(context,msg);
    }

    @Override
    public void toastLong(String msg) {
        ToastUtils.showLong(context,msg);

    }
}
