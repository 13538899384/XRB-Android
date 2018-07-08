package com.ygip.xrb_android.comm.pic_common;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.ImageLoader;
import com.ygip.xrb_android.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 *
 * @description:图片展示适配器
 */
public class SelectMultiPicAdapter extends BaseAdapter {

    public final static String TAG = "SelectPhotoAdapter";

    private Context mContext;
    private List<String> mData;
    private int mLayoutId;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mSelectedPaths = new ArrayList<>();
    private int mCanSelectNum;
    private onChangeSelectedPicNumListener mOnChangeSelectedPicNumListener;

    public SelectMultiPicAdapter(Context context, List<String> datas) {
        mContext = context;
        mData = datas;
        mLayoutId = R.layout.item_select_multi_pic;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null){
            viewHolder = (ViewHolder) convertView.getTag();
        }else {
            convertView = mLayoutInflater.inflate(mLayoutId, null);
            viewHolder = new ViewHolder(convertView);
        }
        convertView.setTag(viewHolder);

        Boolean isSelected = mSelectedPaths.contains(mData.get(position));
        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(mData.get(position), viewHolder.iv_image, isSelected);
        if (isSelected){
            selectImg(viewHolder);
        }else{
            cancelSelectImg(viewHolder);
        }

        viewHolder.rl_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.isSelected == 0){
                    Log.i(TAG, "现在选中");
                    if (mSelectedPaths.size() < mCanSelectNum){
                        mSelectedPaths.add(mData.get(position));
                        mOnChangeSelectedPicNumListener.changeSelectedPicNum(mSelectedPaths.size());
                        selectImg(viewHolder);
                        changeImgBgGray(viewHolder.iv_image);
                    }else{
                        ToastUtils.show("你最多只能选择"+mCanSelectNum+"张图片");
                    }
                }else{
                    Log.i(TAG, "取消选中");
                    mSelectedPaths.remove(mData.get(position));
                    mOnChangeSelectedPicNumListener.changeSelectedPicNum(mSelectedPaths.size());
                    cancelSelectImg(viewHolder);
                    resetImgBg(viewHolder.iv_image);
                }
            }
        });

//        viewHolder.rl_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, mData.get(position));
//                ShowBigImgActivity.startActivity((Activity) mContext, mData.get(position), Constant.LOCAL);
//            }
//        });

        return convertView;
    }

    // 图片被选择
    private void selectImg(ViewHolder viewHolder){
        viewHolder.isSelected = 1;
        viewHolder.iv_select_image.setImageResource(R.drawable.ic_checked);
    }

    // 图片没被选择或取消选择
    private void cancelSelectImg(ViewHolder viewHolder){
        viewHolder.isSelected = 0;
        viewHolder.iv_select_image.setImageResource(R.drawable.ic_check_normal);
    }

    // 下面这两个是选择心墙图片用的(让图片背景变灰)
    // 让图片背景变暗
    private void changeImgBgGray(ImageView iv){
        Drawable drawable = iv.getDrawable();
        if (drawable != null){
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            iv.setImageDrawable(drawable);
        }
    }

    // 让图片背景恢复原样
    private void resetImgBg(ImageView iv){
        Drawable drawable = iv.getDrawable();
        if (drawable != null){
            drawable.clearColorFilter();
            iv.setImageDrawable(drawable);
        }
    }

    private static class ViewHolder {
        private RelativeLayout rl_image;
        private ImageView iv_image;
        private RelativeLayout rl_select_image;
        private ImageView iv_select_image;
        private int isSelected;

        ViewHolder(View view) {
            isSelected = 0;
            rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
            rl_select_image = (RelativeLayout) view.findViewById(R.id.rl_select_image);
            iv_select_image= (ImageView) view.findViewById(R.id.iv_select_image);
        }
    }

    public ArrayList<String> getmSelectedPaths() {
        return mSelectedPaths;
    }

    public void setSelectedPaths(ArrayList<String> mSelectedPaths) {
        this.mSelectedPaths = mSelectedPaths;
    }

    public int getCanSelectNum() {
        return mCanSelectNum;
    }

    public void setmCanSelectNum(int mCanSelectNum) {
        this.mCanSelectNum = mCanSelectNum;
    }

    public interface onChangeSelectedPicNumListener{
        void changeSelectedPicNum(int num);
    }

    public void setOnChangeSelectedPicNum(onChangeSelectedPicNumListener mOnChangeSelectedPicNumListener) {
        this.mOnChangeSelectedPicNumListener = mOnChangeSelectedPicNumListener;
    }

    public void setData(List<String> mData) {
        this.mData = mData;
    }

}

