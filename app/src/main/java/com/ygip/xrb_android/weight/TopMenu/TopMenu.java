package com.ygip.xrb_android.weight.TopMenu;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ygip.xrb_android.App;
import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.DensityUtils;
import com.ygip.xrb_android.util.ListViewUtils;
import com.ygip.xrb_android.util.WindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：17/5/7 on 下午12:13
 * 描述:  右上角弹出菜单
 * 作者:  wenghaobin
 */
public class TopMenu {
    private final String TAG = getClass().getSimpleName();

    protected Activity mContext;
    private PopupWindow mPopupWindow;
    private ListView lv;
    private View popupWindowView;

    private TopMenuAdapter mAdapter;
    private List<MenuItem> menuItems;

    private boolean needAnimationStyle = true;
    private int backgroundId;

    private static final int DEFAULT_ANIM_STYLE = R.style.TRM_ANIM_STYLE;
    private int animationStyle;

    private float alpha = 0.75f;

    private OnMenuItemClickListener onMenuItemClickListener;

    public TopMenu(Activity context) {
        this.mContext = context;
        init();
    }

    private void init() {
        popupWindowView = LayoutInflater.from(mContext).inflate(R.layout.menu_top, null);
        lv = (ListView) popupWindowView.findViewById(R.id.lv);
        menuItems = new ArrayList<>();
        mAdapter = new TopMenuAdapter(mContext, menuItems);
    }

    private PopupWindow getPopupWindow(){
        if (mPopupWindow == null){
            mAdapter.setData(menuItems);
            lv.setAdapter(mAdapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onMenuItemClickListener.onMenuItemClick(position);
                }
            });
            popupWindowView.setBackground(mContext.getResources().getDrawable(backgroundId));

            // 宽度在listview的item的最大宽度的基础上再加8dp, 避免右边太窄导致文字直接去到下一行
            int itemWidth = ListViewUtils.getListViewWidth(lv) + DensityUtils.dip2px(App.getInstance(), 8);
            // 最小宽度100dp
            int minWidth = DensityUtils.dip2px(App.getInstance(), 100);
            int popupWindowWidth = itemWidth > minWidth ? itemWidth : minWidth;

            mPopupWindow = new PopupWindow(popupWindowView, popupWindowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (needAnimationStyle){
                mPopupWindow.setAnimationStyle(animationStyle <= 0 ? DEFAULT_ANIM_STYLE : animationStyle);
            }
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowUtils.setWindowAlpha(mContext, false);
                }
            });
        }
        return mPopupWindow;
    }

    /**
     * 添加单个菜单
     * @param item
     * @return
     */
    public TopMenu addMenuItem(MenuItem item){
        menuItems.add(item);
        return this;
    }

    /**
     * 添加多个菜单
     * @param list
     * @return
     */
    public TopMenu addMenuList(List<MenuItem> list){
        menuItems.addAll(list);
        return this;
    }

    /**
     * 否是需要动画
     * @param need
     * @return
     */
    public TopMenu needAnimationStyle(boolean need){
        this.needAnimationStyle = need;
        return this;
    }

    /**
     * 设置动画
     * @param style
     * @return
     */
    public TopMenu setAnimationStyle(int style){
        this.animationStyle = style;
        return this;
    }

    public TopMenu setBackground(int backgroundId){
        this.backgroundId = backgroundId;
        return this;
    }

    public TopMenu setOnMenuItemClickListener(OnMenuItemClickListener listener){
        onMenuItemClickListener = listener;
        return this;
    }

    public TopMenu showAsDropDown(View anchor, int xoff, int yoff){
        if (mPopupWindow == null){
            getPopupWindow();
        }
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
            WindowUtils.setWindowAlpha(mContext, true);
        }
        return this;
    }

    public void dismiss(){
        if (mPopupWindow != null && mPopupWindow.isShowing()){
            mPopupWindow.dismiss();
        }
    }

    public interface OnMenuItemClickListener{
        void onMenuItemClick(int position);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }
}
