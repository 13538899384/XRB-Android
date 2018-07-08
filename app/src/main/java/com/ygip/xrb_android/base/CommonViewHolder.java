package com.ygip.xrb_android.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ygip.xrb_android.App;

/**
 * Created by Administrator on 2016/8/31.
 */
public class CommonViewHolder {

    private final SparseArray<View> mViews;
    private View mConverView;
    private Context mContext;

    private CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mContext = App.getInstance();
        this.mViews = new SparseArray<View>();
        mConverView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        //setTag
        mConverView.setTag(this);
    }

    public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        CommonViewHolder holder = null;
        if (convertView == null) {
            holder = new CommonViewHolder(context, parent, layoutId, position);
        } else {
            holder = (CommonViewHolder) convertView.getTag();
        }
        return holder;
    }

    public View getConvertView() {
        return mConverView;
    }

    /**
     * @desciption:通过id获取到view
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @description:设置文字
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * @description:设置按钮文字
     * @param viewId
     * @param text
     * @return
     */
    public CommonViewHolder setBtnText(int viewId, String text) {
        Button view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * @description:通过id设置图片
     * @param viewId
     * @param drawableId
     * @return
     */
    public CommonViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * @description:通过bitmap设置图片
     * @param viewId
     * @param bitmap
     * @return
     */
    public CommonViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
}
