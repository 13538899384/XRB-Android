package com.ygip.xrb_android.mvp.login.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.login.present.LoginPresent;
import com.ygip.xrb_android.util.GlideCircleTransform;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by XQM on 2017/7/22.
 */

public class LoginActivity extends XActivity<LoginPresent> {
    @BindView(R.id.img_login)
    ImageView imgLogin;
//    @BindView(R.id.edt_account)
//    EditText edtAccount;
//    @BindView(R.id.edt_password)
//    EditText edtPassword;

    @Override
    public void initData(Bundle savedInstanceState) {
        showInitData();
    }

    private void showInitData() {
        Glide.with(this)
                .load(R.drawable.jidilogo)
                .transform(new GlideCircleTransform(this))
                .animate(R.anim.login_anim)
                .into(imgLogin);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
//        String tvAccount = edtAccount.getText().toString().trim();
//        String tvPassword = edtPassword.getText().toString().trim();

        getP().getUserLoginInfo("ming","0000","13538899384","负责人");
    }
}
