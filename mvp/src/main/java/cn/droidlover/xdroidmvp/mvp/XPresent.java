package cn.droidlover.xdroidmvp.mvp;

import android.util.Log;

/**
 * Created by wanglei on 2016/12/29.
 */

public class XPresent<V extends IView> implements IPresent<V> {
    protected final String TAG = getClass().getName();

    private V v;

    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        v = null;
    }

    protected V getV() {
        if (v == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v;
    }

    protected void log(String msg){
        Log.i(TAG, msg);
    }
}
