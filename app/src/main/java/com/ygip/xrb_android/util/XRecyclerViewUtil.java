package com.ygip.xrb_android.util;

import android.content.Context;
import android.view.View;

import com.ygip.xrb_android.R;

import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * @author zhijian
 * @Description:
 * @date 2017/6/2
 */

public class XRecyclerViewUtil {

    // 设置这些参数最好在setAdapter后进行
    public static void setCommonParams(Context context, XRecyclerContentLayout recyclerContentLayout){
        XRecyclerView recyclerView = recyclerContentLayout.getRecyclerView();
        recyclerView.verticalLayoutManager(context);
        recyclerView.horizontalDivider(R.color.C4, R.dimen.px1);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.useDefLoadMoreView();
        recyclerContentLayout.loadingView(View.inflate(context,R.layout.view_loading, null));
        recyclerContentLayout.emptyView(View.inflate(context, R.layout.view_no_data, null));
        recyclerContentLayout.errorView(View.inflate(context, R.layout.view_no_net, null));
        recyclerContentLayout.showLoading();
    }
}
