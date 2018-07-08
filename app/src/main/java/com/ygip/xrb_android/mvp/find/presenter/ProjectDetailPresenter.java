package com.ygip.xrb_android.mvp.find.presenter;

import com.ygip.xrb_android.mvp.find.model.Project;
import com.ygip.xrb_android.mvp.find.view.ProjectDetailActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by lockyluo on 2017/7/29.
 */

public class ProjectDetailPresenter extends XPresent<ProjectDetailActivity> {
    public void loadData(){
        Project project=getV().getProject();
        getV().toast("装载数据");
    }
}
