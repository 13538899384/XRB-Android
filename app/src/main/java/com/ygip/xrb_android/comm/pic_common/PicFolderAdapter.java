package com.ygip.xrb_android.comm.pic_common;

import android.content.Context;
import android.view.View;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.base.CommonAdapter;
import com.ygip.xrb_android.base.CommonViewHolder;
import com.ygip.xrb_android.util.ImageLoader;

import java.util.List;

/**
 * @author zhijian
 * @Description:
 * @date 2017/2/28
 */

public class PicFolderAdapter extends CommonAdapter<ImageFolder> {

    private final static String TAG = "PicFolderAdapter";

    private int checkedPosition = 0;

    public PicFolderAdapter(Context context, List<ImageFolder> mDatas, int mItemLayoutId) {
        super(context, mDatas, mItemLayoutId);
    }

    @Override
    public void convert(int position, CommonViewHolder helper, ImageFolder item) {
        helper.setText(R.id.tv_folder_name, item.getName());
        helper.setText(R.id.tv_pic_num, item.getCount()+"张");

        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(
                item.getFirstImagePath(), helper.getView(R.id.iv_first_image), false);

        if (checkedPosition == position){
            helper.getView(R.id.iv_checked).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.iv_checked).setVisibility(View.GONE);
        }
    }

    public int getCheckedPosition() {
        return checkedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
    }

}
