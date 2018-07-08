package com.ygip.xrb_android.mvp.search.view;

import android.os.Bundle;

import com.ygip.xrb_android.mvp.search.present.MemberBasePresent;

import cn.droidlover.xdroidmvp.mvp.XLazyFragment;

/**
 * Created by XQM on 2017/8/4.
 */

public class MemberBaseFragment extends XLazyFragment<MemberBasePresent> {
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public MemberBasePresent newP() {
        return null;
    }
}
