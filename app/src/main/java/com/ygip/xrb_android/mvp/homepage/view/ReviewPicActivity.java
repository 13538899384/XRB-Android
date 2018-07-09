package com.ygip.xrb_android.mvp.homepage.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.base.Constant;
import com.ygip.xrb_android.mvp.homepage.adapter.ShowMultiPicsPagerAdapter;
import com.ygip.xrb_android.weight.ViewPagerShowPic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 图片预览
 */
public class ReviewPicActivity extends XActivity {
    @BindView(R.id.viewPager)
    ViewPagerShowPic viewPager;
    @BindView(R.id.tv_num)
    TextView tvNum;

    private LayoutInflater mFactory;

    private List<View> mViews = new ArrayList<View>();

    private List<String> mPics= new ArrayList<String>();
    private int mPosition; // 当前是第几张图
    private int mTotal; // 总共几张图
    private String mPicType; // 图片类型,网络图片还是本地图片

    @Override
    public void initData(Bundle savedInstanceState) {
        mPics = getIntent().getStringArrayListExtra(Constant.KEY_PICS);
//        mPosition = getIntent().getIntExtra(Constant.KEY_PIC_POSITION, Constant.NOTHING);
        mTotal = mPics.size();

        mFactory = getLayoutInflater();

        for (int i = 0; i < mTotal; i++) {
//            View view = mFactory.inflate(R.layout.view_a_pic, null);
//            mViews.add(view);
        }
        viewPager.setAdapter(new ShowMultiPicsPagerAdapter(this, mViews, mPics));

        // 设置缓存页面为9,就是在9个页面中不断切换都不会重新去实例化页卡(这个是因为页卡滑到后面再滑到前面长按事件会失效)
        viewPager.setOffscreenPageLimit(9);
        viewPager.setCurrentItem(mPosition, false);
//        tv_num.setText(mPosition + 1 + "/" + mTotal);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
//                tv_num.setText(arg0 + 1 + "/" + mTotal);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_review_pic;
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
}
