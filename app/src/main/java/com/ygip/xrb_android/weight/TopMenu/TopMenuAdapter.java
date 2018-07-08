package com.ygip.xrb_android.weight.TopMenu;

import android.content.Context;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.base.CommonAdapter;
import com.ygip.xrb_android.base.CommonViewHolder;

import java.util.List;

/**
 * Created by XQM on 2017/9/5.
 */

public class TopMenuAdapter extends CommonAdapter<MenuItem> {

    public TopMenuAdapter(Context context, List<MenuItem> data) {
        super(context, data, R.layout.item_menu_top);
    }

    @Override
    public void convert(int position, CommonViewHolder helper, MenuItem item) {
        helper.setText(R.id.tv, item.getText());
    }
}
