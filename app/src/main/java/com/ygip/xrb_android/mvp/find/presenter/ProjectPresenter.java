package com.ygip.xrb_android.mvp.find.presenter;


import com.ygip.xrb_android.mvp.find.adapter.ProjectAdapter;
import com.ygip.xrb_android.mvp.find.model.Project;
import com.ygip.xrb_android.mvp.find.view.FindFragment;
import com.ygip.xrb_android.util.DateUtils;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;


/**
 * Created by lockyluo on 2017/7/28.
 */

public class ProjectPresenter extends XPresent<FindFragment> {
    private List<Project> projects;

    public List<Project> loadData() {

        projects=new ArrayList<>();
        for(int i=0;i<2;i++)
        {
            projects.add(createNullProject(i));
        }

        return projects;
    }

    public void refreshData(int page,XRecyclerContentLayout xRecyclerContentLayout, ProjectAdapter projectAdapter, List<Project> projects){

        if(Kits.Empty.check(projects)){
            xRecyclerContentLayout.showEmpty();
        }
        projectAdapter.addData(projects);
        getV().setProjects(projectAdapter.getDataSource());

//        xRecyclerContentLayout.getSwipeRefreshLayout().postDelayed(()->
//                xRecyclerContentLayout.getSwipeRefreshLayout().setRefreshing(false),1000);
    }

    public void loadMoreData(int page,XRecyclerContentLayout xRecyclerContentLayout, ProjectAdapter projectAdapter, List<Project> projects){

        if(Kits.Empty.check(projects)){
            xRecyclerContentLayout.showEmpty();
        }
        //this.projects.add(createNullProject(1));
        projectAdapter.addData(this.projects);

        getV().setProjects(projectAdapter.getDataSource());

    }

    private Project createNullProject(int i) {
        Project pro;
        pro = new Project();
        //pro.setLogo("");
        pro.setProjectName("未命名"+i);
        pro.setMembers(new String[]{"研发部成员"+i});
        pro.setFinishedDate(DateUtils.getCurrentTime());
        //pro.setScreenShots(new String[]{""});
        return pro;
    }
}
