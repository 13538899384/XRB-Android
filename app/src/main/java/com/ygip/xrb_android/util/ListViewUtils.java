package com.ygip.xrb_android.util;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author zhijian
 * @Description:
 * @date 2016/9/28
 */

public class ListViewUtils {

    // 当maxItemNum为-1时,表示ListView的高度就是所有子Item和分割线的高度之和
    public static void setListViewHeight(ListView listView) {
        setListViewHeightBasedOnChildren(listView, -1);
    }

    // maxItemNum最多显示多少个item, 用这个计算item高度时, item的rootLayout不能写死,
    // 当maxItemNum为-1时,表示ListView的高度就是所有子Item和分割线的高度之和
    public static void setListViewHeightBasedOnChildren(ListView listView, int maxItemNum) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = getListViewHeightBasedOnChildren(listView, maxItemNum);
        listView.setLayoutParams(params);
    }

    public static int getListViewHeightBasedOnChildren(ListView listView, int maxItemNum){
        if(listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        int itemNum ;
        if (listAdapter.getCount() <= maxItemNum || maxItemNum == -1){
            itemNum = listAdapter.getCount();
        }else{
            itemNum = maxItemNum;
        }
        for (int i = 0; i < itemNum; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // 用这个计算item高度时, item的rootLayout不能写死
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        return totalHeight + (listView.getDividerHeight() * (itemNum - 1));
    }

    public static void setListViewHeight(ListView listView, int itemNum) {
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = getListViewHeight(listView, itemNum);
        listView.setLayoutParams(params);
    }

    // itemNum显示多少个item, 用这个计算item高度时, ListView的高度就是itemNum个子item和分割线的高度之和
    public static int getListViewHeight(ListView listView, int itemNum) {
        if(listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null || listAdapter.getCount() == 0) {
            return 0;
        }
        int totalHeight ;
        // 计算第0个item的高度, 再用它乘以itemNum就得出itemNum个子item的高度
        View listItem = listAdapter.getView(0, null, listView);
        // 用这个计算item高度时, item的rootLayout不能写死
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight() * itemNum;
        return totalHeight + (listView.getDividerHeight() * (itemNum - 1));
    }

    public static int getListViewWidth(ListView listView){
        if(listView == null) return 0;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int maxWidth = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // 用这个计算item高度时, item的rootLayout不能写死
            listItem.measure(0, 0);
            if (listItem.getMeasuredWidth() > maxWidth){
                maxWidth = listItem.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    public static void slideToTop(ListView listView){
        // 强制让它停止惯性滑动
        listView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_CANCEL, 0, 0, 0));
        listView.setSelection(0);
    }
}
