package com.ygip.xrb_android.mvp.search.view;

import android.os.Bundle;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.search.present.MemberBasePresent;

/**
 * Created by XQM on 2017/8/4.
 */

public class PrincipalFragment extends MemberBaseFragment {
    public static PrincipalFragment newInstance(){
        return new PrincipalFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public MemberBasePresent newP() {
        return super.newP();
    }
}
