package com.ygip.xrb_android.mvp.find.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.find.model.Project;
import com.ygip.xrb_android.mvp.find.presenter.NewProjectPresenter;
import com.ygip.xrb_android.util.DialogUtils;
import com.ygip.xrb_android.util.ToastUtils;
import com.ygip.xrb_android.util.ViewDelegateByLocky;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class NewProjectActivity extends XActivity<NewProjectPresenter> {

    ViewDelegateByLocky vDelegate;
    @BindView(R.id.newProject_projectName)
    EditText newprojectProjectName;
    @BindView(R.id.newProject_DatePicker)
    DatePicker newProjectDatePicker;
    @BindView(R.id.newProject_scrollview)
    ScrollView newProjectScrollview;
    private ArrayList<String> logo = new ArrayList<>();
    private Project project;
    public static boolean isSaved = false;
    private Calendar calendar=Calendar.getInstance();

    @BindView(R.id.newproject_sp_project_type)
    Spinner newprojectSpProjectType;
    @BindView(R.id.newproject_ll_project_member)
    LinearLayout newprojectLlProjectMember;
    @BindView(R.id.newproject_ll_add_project_member)
    ImageButton newprojectLlAddProjectMember;
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
//    @BindView(R.id.newProject_select_logo)
//    MultiPickResultView newProjectSelectLogo;


    @Override
    public int getLayoutId() {
        return R.layout.activity_new_project;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

//        initView();
//        project = getP().loadData(context);
        if (project!=null) {
            try {
                newprojectProjectName.setText(project.getProjectName());
                newprojectSpProjectType.setSelection(project.getProjectType());
//                Logger.d(project.getLogo());

                if(project.getLogo()!=null)
                {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(project.getLogo());
//                    newProjectSelectLogo.showPics(list);
                }
            } catch (Exception e) {
//                Logger.e(e.getMessage());
            }

        }
    }

    void initView() {
        titlebarTvTitle.setText("新建项目详情");
        titlebarLlLeft.setVisibility(View.VISIBLE);
        //titlebarLlRight.setVisibility(View.VISIBLE);

        titlebarTvRight.setVisibility(View.VISIBLE);
        titlebarTvRight.setText("保存");

//        newProjectSelectLogo.init(context, 8, MultiPickResultView.ACTION_SELECT, logo);//图片选择器
        getRxPermissions()
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granded -> {
                    if (granded) {
//                        newProjectSelectLogo.setVisibility(View.VISIBLE);//有sd权限则显示选择器
                    } else {
//                        newProjectSelectLogo.setVisibility(View.GONE);
                        ToastUtils.show("sd卡权限被拒绝，无法访问图片");
                        //finish();
                    }
                });

        newprojectSpProjectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //ToastUtils.show(context, position + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        newProjectDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
    }

//    private boolean checkNull() {
//        if (newprojectProjectName.getText() != null
//                && newprojectSpProjectType.getSelectedItemPosition() != 0
//                && newProjectSelectLogo.getPhotos() != null
//                ) {
//            try {
//                newProjectSelectLogo.getPhotos().get(0);
//                return false;
//            } catch (Exception e) {
//                Logger.e(e.getMessage());
//                ToastUtils.show("未选择图片");
//            }
//
//        } else {
//            ToastUtils.show("信息未填写完整");
//        }
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        newProjectSelectLogo.onActivityResult(requestCode, resultCode, data);
//        logo = newProjectSelectLogo.getPhotos();//获取返回的图片地址
    }


    @Override
    public NewProjectPresenter newP() {
        return new NewProjectPresenter();
    }

    public ViewDelegateByLocky getvDelegate() {
        if (vDelegate == null) {
            vDelegate = ViewDelegateByLocky.create(context);
        }
        return vDelegate;
    }


//    private Project buildData() {
//        if (!checkNull()) {
//            project = new Project();
//            project.setProjectName(newprojectProjectName.getText().toString());
//
//            try {
//                logo = newProjectSelectLogo.getPhotos();
//                project.setLogo(logo.get(0));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            project.setProjectType(newprojectSpProjectType.getSelectedItemPosition());
//            return project;
//        }
//        return null;
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isSaved) {
                DialogUtils.dialogFinish(context);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.titlebar_tv_title)
    public void onTitlebarTvTitleClicked() {

    }

    @OnClick(R.id.titlebar_ll_left)
    public void onTitlebarLlLeftClicked() {
        if (!isSaved) {
            DialogUtils.dialogFinish(context);
        } else {
            finish();
        }

    }

    @OnClick(R.id.titlebar_iv_right)
    public void onTitlebarIvRightClicked() {
    }

    @OnClick(R.id.titlebar_ll_right)
    public void onTitlebarLlRightClicked() {
    }

    @OnClick(R.id.titlebar_tv_right)
    public void onTitlebarTvRightClicked() {
//        if (buildData() != null) {
//            getP().save(project);
//            isSaved = true;
//        }

    }

    @OnClick(R.id.newproject_ll_add_project_member)
    public void onNewprojectLlAddProjectMemberClicked() {

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSaved=false;
    }
}
