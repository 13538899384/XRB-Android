package com.ygip.xrb_android.mvp.search.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.search.adapter.MemberAdapter;
import com.ygip.xrb_android.mvp.search.model.Member;
import com.ygip.xrb_android.mvp.search.present.MemberPresent;
import com.ygip.xrb_android.util.XRecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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

public class SearchFragment extends XLazyFragment<MemberPresent> {
    @BindView(R.id.titlebar_tv_title)
    TextView titlebarTvTitle;
    @BindView(R.id.xRecyclerContentLayout)
    XRecyclerContentLayout xRecyclerContentLayout;
    @BindView(R.id.titlebar_ll_left)
    LinearLayout titlebarLlLeft;
    Unbinder unbinder;
    private MemberAdapter adapter;
    private List<Member> members = new ArrayList<>();
    Member member;


    @Override
    public void initData(Bundle savedInstanceState) {
        titlebarTvTitle.setText("成员");
        member = new Member("徐庆明", "2015级", "负责人");
        members.add(member);
        initAdapter();
    }


    private void initAdapter() {
        if (adapter == null) {
            adapter = new MemberAdapter(context);
        }
        XRecyclerView xRecyclerView = xRecyclerContentLayout.getRecyclerView();
        XRecyclerViewUtil.setCommonParams(context, xRecyclerContentLayout);
        xRecyclerView.setAdapter(adapter);
        adapter.addData(members);
        xRecyclerContentLayout.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Kits.Empty.check(members)) {
                    xRecyclerContentLayout.showEmpty();
                }
                adapter.addData(members);
            }
        });
        xRecyclerView.setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefresh() {
                xRecyclerContentLayout.getSwipeRefreshLayout().setRefreshing(false);
                if (Kits.Empty.check(members)) {
                    xRecyclerContentLayout.showEmpty();
                }
                adapter.addData(members);
            }

            @Override
            public void onLoadMore(int page) {
                adapter.addData(members);
            }
        });
        adapter.setRecItemClick(new RecyclerItemCallback<Member, MemberAdapter.ViewHolder>() {
            @Override
            public void onItemClick(int position, Member model, int tag, MemberAdapter.ViewHolder holder) {
                super.onItemClick(position, model, tag, holder);
                Router.newIntent(context).to(MemberItemActivity.class).launch();
            }
        });
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    public MemberPresent newP() {
        return new MemberPresent();
    }

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.btnPai)
    public void onViewClicked() {
        Router.newIntent(context)
                .to(PhoneActivity.class)
                .launch();
    }

}
