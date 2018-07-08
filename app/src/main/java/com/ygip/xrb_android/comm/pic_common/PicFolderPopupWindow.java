package com.ygip.xrb_android.comm.pic_common;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.DensityUtils;
import com.ygip.xrb_android.util.WindowUtils;

import java.util.List;

/**
* @author zhijian
* @Description:
* @date 2017/2/28
*/

public class PicFolderPopupWindow {

    public static final int bottombarHeight = 48; // 单位dp

    public static final String TAG = "PicFolderPopupWindow";

    //上下文对象
    private Context mContext;

    private List<ImageFolder> mImageFolders;
    //PopupWindow对象
    private PopupWindow mPopupWindow;
    //点击事件
    private onListViewItemClickListener mOnListViewItemClickListener;

    private PicFolderAdapter mAdapter;

    private View popupWindow_view;

    private ListView listView;

    public PicFolderPopupWindow(Context context) {
        mContext = context;
    }

    public PicFolderPopupWindow(Context context, List<ImageFolder> imageFolders) {
        mContext = context;
        mImageFolders = imageFolders;
        init();
    }

    /** * 弹出Popupwindow */
    public void init() {
        popupWindow_view = LayoutInflater.from(mContext).inflate(R.layout.listview_pic_folder, null);
        listView = (ListView) popupWindow_view.findViewById(R.id.lv_pic_folder);
        mAdapter = new PicFolderAdapter(mContext, mImageFolders, R.layout.item_pic_folder);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setCheckedPosition(position);
                mAdapter.notifyDataSetChanged();
                mOnListViewItemClickListener.onItemClick(parent, view, position, id);
            }
        });

        mPopupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(mContext, 400));
        //  mPopupWindow.setAnimationStyle(R.style.timepopwindow_anim_style);
        mPopupWindow.setAnimationStyle(R.style.popwindow_anim_bottom_style);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpa(false);
            }
        });
    }

    /** * 显示PopupWindow */
    public void show() {
        if (mPopupWindow != null && !mPopupWindow.isShowing()) {
            mPopupWindow.showAtLocation(popupWindow_view, Gravity.BOTTOM, 0, DensityUtils.dip2px(mContext, 48));
        }
        setWindowAlpa(true);
    }

    /** * 消失PopupWindow */
    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /** * 获取PopupWindow当前是否在显示 */
    public boolean isShowing() {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    /** * 动态设置Activity背景透明度 * * @param isopen */
    public void setWindowAlpa(boolean isopen) {
        WindowUtils.setWindowAlpha(mContext, isopen);
    }

    public interface onListViewItemClickListener {
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }

    public void setmOnListViewItemClickListener(onListViewItemClickListener mOnListViewItemClickListener) {
        this.mOnListViewItemClickListener = mOnListViewItemClickListener;
    }
}

