package com.ygip.xrb_android.mvp.find.view;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.find.model.Project;
import com.ygip.xrb_android.mvp.find.presenter.ProjectDetailPresenter;
import com.ygip.xrb_android.util.ToastUtils;

import butterknife.BindView;

import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.VDelegate;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class ProjectDetailActivity extends XActivity<ProjectDetailPresenter> {
    public Project getProject() {
        return project;
    }

    private Project project;

    @BindView(R.id.titlebar_tv_title)
    TextView titlebarTvTitle;
    @BindView(R.id.titlebar_ll_left)
    LinearLayout titlebarLlLeft;
    @BindView(R.id.titlebar_iv_right)
    ImageView titlebarIvRight;
    @BindView(R.id.titlebar_ll_right)
    LinearLayout titlebarLlRight;
    @BindView(R.id.titlebar_tv_right)
    TextView titlebarTvRight;

    @Override
    public void initData(Bundle savedInstanceState) {
        project=(Project) getIntent().getSerializableExtra("data");
        if(Kits.Empty.check(project)){
            titlebarTvTitle.setText("项目详情");
        }else {
            titlebarTvTitle.setText(project.getProjectName()+"项目");
            getP().loadData();
        }

        titlebarLlLeft.setVisibility(View.VISIBLE);
        titlebarLlRight.setVisibility(View.VISIBLE);
        titlebarTvRight.setVisibility(View.VISIBLE);
        titlebarTvRight.setText("保存");
    }


    public void toast(String str){
        ToastUtils.show(context,str);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_project_detail;
    }

    @Override
    public ProjectDetailPresenter newP() {
        return new ProjectDetailPresenter();
    }



    @OnClick(R.id.titlebar_tv_title)
    public void onTitlebarTvTitleClicked() {
    }

    @OnClick(R.id.titlebar_ll_left)
    public void onTitlebarLlLeftClicked() {
        finish();
    }

    @OnClick(R.id.titlebar_iv_right)
    public void onTitlebarIvRightClicked() {
    }

    @OnClick(R.id.titlebar_ll_right)
    public void onTitlebarLlRightClicked() {
    }

    @OnClick(R.id.titlebar_tv_right)
    public void onTitlebarTvRightClicked() {

    }
}
