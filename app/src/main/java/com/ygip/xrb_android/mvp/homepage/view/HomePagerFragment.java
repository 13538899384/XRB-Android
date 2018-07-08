package com.ygip.xrb_android.mvp.homepage.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.homepage.adapter.HomepageAdapter;
import com.ygip.xrb_android.mvp.homepage.model.Dynamic;
import com.ygip.xrb_android.mvp.search.present.MemberPresent;
import com.ygip.xrb_android.mvp.search.view.MemberItemActivity;
import com.ygip.xrb_android.util.XRecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.droidlover.xdroidmvp.kit.Kits;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.router.Router;
import cn.droidlover.xrecyclerview.RecyclerItemCallback;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by XQM on 2017/7/23.
 */

public class HomePagerFragment extends XLazyFragment<MemberPresent> implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    @BindView(R.id.xRecyclerContentLayout)
    XRecyclerContentLayout xRecyclerContentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    private HomepageAdapter adapter;
    private List<Dynamic> dynamics = new ArrayList<>();
    private Dynamic dynamic;

    @Override
    public void initData(Bundle savedInstanceState) {
        dynamic = new Dynamic();
        dynamics.add(dynamic);
        initAdapter();
    }

    public static HomePagerFragment newInstance(String s) {

        Bundle args = new Bundle();

        HomePagerFragment fragment = new HomePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new HomepageAdapter(context);
        }
        XRecyclerView xRecyclerView = xRecyclerContentLayout.getRecyclerView();
        XRecyclerViewUtil.setCommonParams(context, xRecyclerContentLayout);
        xRecyclerView.setAdapter(adapter);
        adapter.addData(dynamics);
        xRecyclerContentLayout.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Kits.Empty.check(dynamics)) {
                    xRecyclerContentLayout.showEmpty();
                }
                adapter.addData(dynamics);
            }
        });
        xRecyclerView.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                xRecyclerContentLayout.getSwipeRefreshLayout().setRefreshing(false);
                if (Kits.Empty.check(dynamics)) {
                    xRecyclerContentLayout.showEmpty();
                }
                adapter.addData(dynamics);
            }

            @Override
            public void onLoadMore(int page) {
                adapter.addData(dynamics);
            }
        });
        adapter.setRecItemClick(new RecyclerItemCallback<Dynamic, HomepageAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, Dynamic model, int tag, HomepageAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                Router.newIntent(context).to(MemberItemActivity.class).launch();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public MemberPresent newP() {
        return null;
    }

    public static HomePagerFragment newInstance() {

        Bundle args = new Bundle();

        HomePagerFragment fragment = new HomePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
