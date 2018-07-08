package com.ygip.xrb_android.mvp.search.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.base.Constant;
import com.ygip.xrb_android.base.PicUploadModel;
import com.ygip.xrb_android.comm.pic_common.GridPicAdapter;
import com.ygip.xrb_android.comm.pic_common.SelectMultiPicActivity;
import com.ygip.xrb_android.util.AddImageUtils;
import com.ygip.xrb_android.util.ImageMarkUtils;
import com.ygip.xrb_android.util.KeyBoardUtils;
import com.ygip.xrb_android.util.ToastUtils;
import com.ygip.xrb_android.weight.AlertDialogV7;
import com.ygip.xrb_android.weight.ExpandGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by XQM on 2017/8/30.
 */

public class PhoneActivity extends XActivity {
    @BindView(R.id.gv_new_multi_pic)
    ExpandGridView gvNewMultiPic;

    private final String emptyStr = "";
    @BindView(R.id.sour_pic)
    ImageView sourPic;
    @BindView(R.id.wartermark_pic)
    ImageView wartermarkPic;
    private List<String> mPicPaths = new ArrayList<>();
    private PicUploadModel mPicUploadModel = new PicUploadModel();
    private GridPicAdapter mPicAdapter;

    @Override
    public void initData(Bundle savedInstanceState) {
        initExpandAdapter();
        setWaterMarkPic();
    }

    private void setWaterMarkPic() {
        Bitmap sourBitmap  = BitmapFactory.decodeResource(getResources(),R.drawable.game_of_thrones);
        sourPic.setImageBitmap(sourBitmap);

        Bitmap waterBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hannibal);
        Bitmap watermarkBitmap = ImageMarkUtils.createWaterMaskCenter(sourBitmap, waterBitmap);
        watermarkBitmap = ImageMarkUtils.createWaterMaskLeftBottom(this, watermarkBitmap, waterBitmap, 0, 0);
        watermarkBitmap = ImageMarkUtils.createWaterMaskRightBottom(this, watermarkBitmap, waterBitmap, 0, 0);
        watermarkBitmap = ImageMarkUtils.createWaterMaskLeftTop(this, watermarkBitmap, waterBitmap, 0, 0);
        watermarkBitmap = ImageMarkUtils.createWaterMaskRightTop(this, watermarkBitmap, waterBitmap, 0, 0);

        Bitmap textBitmap = ImageMarkUtils.drawTextToLeftTop(this, watermarkBitmap, "左上角", 16, Color.RED, 0, 0);
        textBitmap = ImageMarkUtils.drawTextToRightBottom(this, textBitmap, "右下角", 16, Color.RED, 0, 0);
        textBitmap = ImageMarkUtils.drawTextToRightTop(this, textBitmap, "右上角", 16, Color.RED, 0, 0);
        textBitmap = ImageMarkUtils.drawTextToLeftBottom(this, textBitmap, "左下角", 16, Color.RED, 0, 0);
        textBitmap = ImageMarkUtils.drawTextToCenter(this, textBitmap, "中间", 16, Color.RED);

        wartermarkPic.setImageBitmap(textBitmap);
    }

    private void initExpandAdapter() {
        mPicPaths.add(emptyStr);
        mPicAdapter = new GridPicAdapter(this, mPicPaths);
        mPicAdapter.setOnDeletePic(new GridPicAdapter.OnDeletePic() {
            @Override
            public void deletePic(String item, int position) {
                deleteImage(item, position);
            }
        });
        gvNewMultiPic.setAdapter(mPicAdapter);
        gvNewMultiPic.setOnItemClickListener((parent, view, position, id) -> {
            if (emptyStr.equals((mPicPaths.get(position)))) {
                KeyBoardUtils.getInstance().hideKeyboard(this);
                getRxPermissions().request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                showActionSheet();
                            }
                        });
            }
        });
    }

    private void showActionSheet() {
        new AlertDialog.Builder(this).setItems(new String[]{"拍照", "相册"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    AddImageUtils.takePhoto(this, Constant.imgCachePath);
                    break;
                case 1:
                    Intent intent = new Intent(this, SelectMultiPicActivity.class);
                    intent.putExtra("canSelectNum", getCanSelectNum());
                    startActivityForResult(intent, AddImageUtils.REQUEST_CODE_ALBUM);
                    break;
                default:
                    break;
            }
        }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            doSelectedPic(requestCode, data);
        }
    }

    private void doSelectedPic(int requestCode, Intent data) {
        switch (requestCode) {
            case AddImageUtils.REQUEST_CODE_ALBUM: {// 获取相册图片
                ArrayList<String> selectedImages = data.getStringArrayListExtra(Constant.KEY_PICS);
                if (Kits.Empty.check(selectedImages)) return;
                for (String path : selectedImages) {
                    addOriginPicPath(path);
                    mPicUploadModel.addPics(selectedImages);
                }
//                mPicUploadModel.addPics(selectedImages);
                break;
            }
            case AddImageUtils.REQUEST_CODE_TACKPHONE: {// 获取拍照图片
                try {
                    if (data == null) {
                        if (AddImageUtils.getPhotoFile() != null) {
                            String path = AddImageUtils.getPhotoFile().toString();
                            addOriginPicPath(path);
                            mPicUploadModel.addPic(path);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            default:
                break;
        }
    }

    private void addOriginPicPath(String path) {
        if (!isFullPics()) {
            mPicPaths.remove(emptyStr);
            mPicPaths.add(path);
            if (mPicPaths.size() < Constant.MAX_PICS) {
                mPicPaths.add(emptyStr);
            }
            mPicAdapter.notifyDataSetChanged();
        }
    }

    private boolean isFullPics() {
        int size = mPicPaths.size();
        return size >= 10 && !emptyStr.equals(mPicPaths.get(size - 1));
    }

    private int getCanSelectNum() {
        return 10 - (mPicPaths.size() - 1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_phone;
    }

    @Override
    public Object newP() {
        return null;
    }

    private void deleteImage(String data, int position) {
        new AlertDialogV7(this).setMsg("要删除这张图片吗?").setPosBtn("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeOriginPicPath(data);
                mPicUploadModel.removePic(position);
            }
        }).setNegBtn("取消", (dialog, which) -> {
        }).show().setPosBtnTxtCol().setNegBtnTxtCol();
    }

    private void removeOriginPicPath(String data) {
        int size = mPicPaths.size();
        if (size == Constant.MAX_PICS && !emptyStr.equals(mPicPaths.get(size - 1))) {
            mPicPaths.add(emptyStr);
        }
        mPicPaths.remove(data);
        mPicAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.btn_ya)
    public void onViewClicked() {
        if (checkEmpty()) return;
        log(mPicUploadModel.getBase64Strs().toString());
    }

    private boolean checkEmpty() {
        if (mPicPaths.contains(emptyStr) && (mPicUploadModel.getBase64Strs().size() + 1) != mPicPaths.size()) {
            ToastUtils.show("图片正在压缩, 请稍候再试");
            return true;
        } else if (!mPicPaths.contains(emptyStr) && mPicUploadModel.getBase64Strs().size() != mPicPaths.size()) {
            ToastUtils.show("图片正在压缩, 请稍候再试");
            return true;
        }
        return false;
    }

}
