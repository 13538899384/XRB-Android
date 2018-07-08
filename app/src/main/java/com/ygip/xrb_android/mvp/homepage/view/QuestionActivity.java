package com.ygip.xrb_android.mvp.homepage.view;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.SDCardUtil;
import com.ygip.xrb_android.weight.RichTextEditor;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.mvp.XActivity;


public class QuestionActivity extends XActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_content)
    RichTextEditor etNewContent;
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        etNewContent.setOnRtImageDeleteListener(new RichTextEditor.OnRtImageDeleteListener() {
            @Override
            public void onRtImageDelete(String imagePath) {
                boolean isOk = SDCardUtil.deleteFile(imagePath);
                if (isOk)
                    showToast("删除成功：" + imagePath);
            }
        });

        etNewContent.setOnRtImageClickListener(new RichTextEditor.OnRtImageClickListener() {
            @Override
            public void onRtImageClick(String imagePath) {
//                List<String> imageList = StringImagePathUtils.getTextFromHtml("", true);
//                int currentPosition = imageList.indexOf(imagePath);
//                showToast("点击图片："+currentPosition+"："+imagePath);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_question;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_group, R.id.iv_select_pic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_group:
                finish();
                break;
            case R.id.iv_select_pic:
                getRxPermissions().request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                Matisse.from(this)
                                        .choose(MimeType.allOf())//图片类型
                                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                                        .maxSelectable(5)//可选的最大数
                                        .capture(true)//选择照片时，是否显示拍照
                                        .captureStrategy(new CaptureStrategy(true, "com.ygip.xrb_android.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                        .imageEngine(new GlideEngine())//图片加载引擎
                                        .forResult(REQUEST_CODE_CHOOSE);//
                            }else{
                                showToast("请开启读取SD卡权限");
                            }
                        });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                    List<Uri> result = Matisse.obtainResult(data);
                } else if (requestCode == REQUEST_CODE_CHOOSE){
                    //异步方式插入图片
                    insertImagesSync(data);
                }
            }
        }
    }

    private void insertImagesSync(Intent data) {

    }
}
