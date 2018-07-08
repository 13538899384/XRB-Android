package com.ygip.xrb_android.comm.pic_common;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.base.CommonAdapter;
import com.ygip.xrb_android.base.CommonViewHolder;

import java.io.File;
import java.util.List;

import cn.droidlover.xdroidmvp.imageloader.ILFactory;

/**
 * @author zhijian
 * @Description:
 * @date 2017/5/22
 */

public class GridPicAdapter extends CommonAdapter<String> {

    private static final String emptyStr = "";
    private OnDeletePic onDeletePic;

    public GridPicAdapter(Context cxt, List<String> datas) {
        super(cxt, datas, R.layout.item_gv_pic_delete);
    }

    @Override
    public void convert(int position, CommonViewHolder helper, String item) {
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        ImageView ic_delete = helper.getView(R.id.iv_delete);
        if (emptyStr.equals(item)) {
            // 显示默认图
            ILFactory.getLoader().loadResource(iv_pic, R.drawable.ic_add, null);
            // 隐藏删除按钮
            ic_delete.setVisibility(View.GONE);
            ic_delete.setOnClickListener(null);
        } else {
            // 显示本地图片
            ILFactory.getLoader().loadFile(iv_pic, new File(item), null);
            // 显示删除按钮
            ic_delete.setVisibility(View.VISIBLE);

            ic_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeletePic != null){
                        onDeletePic.deletePic(item, position);
                    }
                }
            });
        }
    }


    public interface OnDeletePic{
        void deletePic(String item, int position);
    }

    public void setOnDeletePic(OnDeletePic onDeletePic) {
        this.onDeletePic = onDeletePic;
    }
}
