package com.ygip.xrb_android.mvp.mine.view;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.mine.presenter.MinePresenter;
import com.ygip.xrb_android.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lockyluo on 2017/7/28.
 */

public class MineFragment extends XLazyFragment<MinePresenter> {

    @BindView(R.id.iv_mine_head)
    CircleImageView ivMineHead;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.mine_ConstraintLayout)
    CoordinatorLayout mineConstraintLayout;
    Unbinder unbinder;

    @Override
    public void initData(Bundle savedInstanceState) {
//        toolbar.setTitle("我的");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public MinePresenter newP() {
        return new MinePresenter();
    }

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void toast(String s){
        ToastUtils.show(s);
    }

    @OnClick(R.id.iv_mine_head)
    public void onIvMineHeadClicked() {
        toast("iv_mine_head");
    }

//    @OnClick(R.id.toolbar)
//    public void onToolbarClicked() {
//    }
}
