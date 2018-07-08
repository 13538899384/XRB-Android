package com.ygip.xrb_android.mvp.find.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ygip.xrb_android.util.ToastUtils;
import com.ygip.xrb_android.util.ViewDelegateByLocky;


/**
 * Created by lockyluo on 2017/8/3.
 */

public class ProjectModel {
    private static ViewDelegateByLocky viewDelegateByLocky;
    private static Context context;
    private static Thread thread;
    private static ProjectModel projectModel;

    public static void setViewDelegateByLocky(ViewDelegateByLocky viewDelegateByLocky) {
        ProjectModel.viewDelegateByLocky = viewDelegateByLocky;
    }

    public static void setContext(Context context) {
        ProjectModel.context = context;
    }


    public static ProjectModel getInstance() {
        if (projectModel == null) {
            projectModel = new ProjectModel();
        }
        return projectModel;
    }


    public Project getData(Context context,ViewDelegateByLocky viewDelegateByLocky) {
        Project project = new Project();
        try {
            SharedPreferences sp = context.getSharedPreferences("local_data", Context.MODE_PRIVATE);
            String data=sp.getString("project_local", "");
//            Logger.d(data);
            if(data.equals("")){
                return project;
            }
            project = (new Gson()).fromJson(data, Project.class);

        } catch (Exception e) {
//            Logger.e(e.getMessage());
        } finally {
            viewDelegateByLocky.toastShort("model loaded");
            return project;
        }
    }

    private void saveLocalData(Project project) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("local_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        {
            String data = (new Gson()).toJson(project);
            editor = sharedPreferences.edit();
            editor.putString("project_local", data);
            editor.commit();
            ToastUtils.show(context,"model save local");
        }
    }

    public void UploadData(Project project, ViewDelegateByLocky viewDelegateByLocky) {

        context = viewDelegateByLocky.getContext();
        if (thread != null) {
            thread.interrupt();
        }

        thread = new Thread(() -> {

            saveLocalData(project);

            ((Activity) context).runOnUiThread(() ->
                    {
                        //viewDelegateByLocky.toastShort("model已上传")
                    }
            );
        });
        thread.start();

    }


}
