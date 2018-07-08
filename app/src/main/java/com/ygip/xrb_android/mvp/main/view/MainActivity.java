package com.ygip.xrb_android.mvp.main.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.helper.BottomNavigationViewHelper;
import com.ygip.xrb_android.mvp.find.view.FindFragment;
import com.ygip.xrb_android.mvp.homepage.view.HomePagerFragment;
import com.ygip.xrb_android.mvp.main.present.MainPresent;
import com.ygip.xrb_android.mvp.mine.view.MineFragment;
import com.ygip.xrb_android.mvp.search.view.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.base.XFragmentAdapter;
import cn.droidlover.xdroidmvp.mvp.XActivity;

public class MainActivity extends XActivity<MainPresent> {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationView bottomNavigationBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
//    @BindView(R.id.toolbar)

    private MenuItem menuItem;

    private String[] titles = {"首页", "搜索", "发现", "我的"};
    private List<Fragment> fragments = new ArrayList<Fragment>();

    private XFragmentAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void initData(Bundle savedInstanceState) {
        initToolBar();
        initFragments();
        adapter = new XFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0, false);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationBar);
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        bottomNavigationBar.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_news:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.item_lib:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.item_find:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.item_more:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationBar.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationBar.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //禁止ViewPager滑动
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    /**
     * 代码中设置 paddingTop 并添加占位状态栏,高度为状态栏高度，
     * 相当于手动实现了 fitsSystemWindows=true 的效果
     */
    private void initToolBar() {
        //设置 paddingTop
//        ViewGroup rootView = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
//        rootView.setPadding(0, getStatusBarHeight(), 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 以上直接设置状态栏颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.C0));
        } else {
            //根布局添加占位状态栏
            ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.C0));
            decorView.addView(statusBarView, lp);
        }

    }

    /**
     * 显示Toolbar
     *
     * @param show true:显示,false:隐藏
     */
//    public void showToolbar(boolean show) {
//        if (toolbar == null) {
//            Log.e(TAG, "Toolbar is null.");
//        } else {
//            int paddingTop = toolbar.getPaddingTop();
//            int paddingBottom = toolbar.getPaddingBottom();
//            int paddingLeft = toolbar.getPaddingLeft();
//            int paddingRight = toolbar.getPaddingRight();
//            int statusHeight = getStatusBarHeight();
//            ViewGroup.LayoutParams params = toolbar.getLayoutParams();
//            int height = params.height;
//            /**
//             * 利用状态栏的高度，4.4及以上版本给Toolbar设置一个paddingTop值为status_bar的高度，
//             * Toolbar延伸到status_bar顶部
//             **/
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                setTranslucentStatus(show);
//                if (show) {
//                    paddingTop += statusHeight;
//                    height += statusHeight;
//                } else {
//                    paddingTop -= statusHeight;
//                    height -= statusHeight;
//                }
//            }
//            params.height = height;
//            toolbar.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
//            toolbar.setVisibility(show ? View.VISIBLE : View.GONE);
//        }
//    }

    /**
     * 设置透明状态栏
     * 对4.4及以上版本有效
     *
     * @param on
     */
    private void setTranslucentStatus(boolean on){
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initFragments() {
        fragments.add(HomePagerFragment.newInstance());
        fragments.add(SearchFragment.newInstance());
        fragments.add(FindFragment.newInstance());
        fragments.add(MineFragment.newInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainPresent newP() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
