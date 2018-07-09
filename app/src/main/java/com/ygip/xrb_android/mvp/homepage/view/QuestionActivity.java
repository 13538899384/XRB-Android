package com.ygip.xrb_android.mvp.homepage.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.ImageUtils;
import com.ygip.xrb_android.util.KeyBoardUtils;
import com.ygip.xrb_android.util.SDCardUtil;
import com.ygip.xrb_android.util.WindowUtils;
import com.ygip.xrb_android.weight.AlertDialogV7;
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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionActivity extends XActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_content)
    RichTextEditor etNewContent;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private Observable<String> subscription = null;
    private List<Uri> result = null;
    private int reqWidth;
    private int reqHeight;
    private boolean isOk = false;

    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        KeyBoardUtils.getInstance().showKeyboard(this);
        reqWidth = WindowUtils.getScreenWidth(getApplicationContext());
        reqHeight = WindowUtils.getScreenHeight(getApplicationContext());
        etNewContent.setOnRtImageDeleteListener(new RichTextEditor.OnRtImageDeleteListener() {
            @Override
            public void onRtImageDelete(String imagePath) {
//                return showDeleteDialog(imagePath);
            }

        });

        etNewContent.setOnRtImageClickListener(new RichTextEditor.OnRtImageClickListener() {
            @Override
            public void onRtImageClick(String imagePath) {
                //图片预览暂时先不做
//                List<String> imageList = StringImagePathUtils.getTextFromHtml("", true);
//                int currentPosition = imageList.indexOf(imagePath);
//                showToast("点击图片："+currentPosition+"："+imagePath);
            }
        });
    }

    private boolean showDeleteDialog(String imagePath) {
        isOk = false;
        new AlertDialogV7(this).setMsg("确定要删除这张图片吗？")
                .setPosBtn("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isOk = SDCardUtil.deleteFile(imagePath);
                        if (!isOk){
                            showToast("删除失败，请重试！");
                        }
                    }
                })
                .setNegBtn("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        isOk = false;
                    }
                }).show().setNegBtnTxtCol().setPosBtnTxtCol();
        return isOk;

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
                KeyBoardUtils.getInstance().hideKeyboard(this);
                getRxPermissions().request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                getLocalGallery();
                            }else{
                                showToast("请开启读取SD卡权限");
                            }
                        });
                break;
        }
    }

    /**
     * 调用系统的图库
     */
    private void getLocalGallery(){
        Matisse.from(this)
                .choose(MimeType.allOf())//图片类型
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(5)//可选的最大数
                .capture(true)//选择照片时，是否显示拍照
                .captureStrategy(new CaptureStrategy(true, "com.ygip.xrb_android.fileprovider"))//参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .imageEngine(new GlideEngine())//图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE);//返回结果
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                    result = Matisse.obtainResult(data);
                } else if (requestCode == REQUEST_CODE_CHOOSE){
                    //异步方式插入图片
                    insertImagesSync(data);
                }
            }
        }
    }

    private void insertImagesSync(final Intent data) {
        //创建被观察者
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //执行其他操作
                etNewContent.measure(0,0);
                result = Matisse.obtainResult(data);
                if (result == null)return;
                for (Uri uri:result){
                    String imagePath = SDCardUtil.getFilePathFromUri(getApplicationContext(),uri);
                    Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath,reqWidth,reqHeight);
                    imagePath = SDCardUtil.saveToSdCard(bitmap);
                    log(imagePath);

                    //执行完通知观察者
                    e.onNext(imagePath);
                }

            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String imagePath) {
//                        log("我接收到数据了"+imagePath);
//                        Glide.with(getApplicationContext()).load(imagePath).into(ivCeshi);
                        etNewContent.insertImage(imagePath,etNewContent.getMeasuredWidth());
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("图片插入失败:"+e.getMessage());
                        log(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String value) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }
    }
}
