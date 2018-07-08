package com.ygip.xrb_android.mvp.search.view;

import android.os.Bundle;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.search.present.MemberBasePresent;

/**
 * Created by XQM on 2017/8/4.
 */

public class ProductDeptFragment extends MemberBaseFragment {
    public static ProductDeptFragment newInstance(){
        return new ProductDeptFragment();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_member_item;
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
