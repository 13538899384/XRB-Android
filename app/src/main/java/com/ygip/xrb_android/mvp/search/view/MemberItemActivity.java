package com.ygip.xrb_android.mvp.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.search.present.MemberItemPresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by XQM on 2017/7/23.
 */

public class MemberItemActivity extends XActivity<MemberItemPresent> {
    @BindView(R.id.titlebar_tv_title)
    TextView titlebarTvTitle;
    @BindView(R.id.titlebar_ll_left)
    LinearLayout titlebarLlLeft;

    @Override
    public void initData(Bundle savedInstanceState) {
        titlebarTvTitle.setText("xxx");
        titlebarLlLeft.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_item;
    }

    @Override
    public MemberItemPresent newP() {
        return new MemberItemPresent();
    }

}
