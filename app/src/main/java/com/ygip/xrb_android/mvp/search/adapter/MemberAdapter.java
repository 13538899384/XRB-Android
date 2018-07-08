package com.ygip.xrb_android.mvp.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.search.model.Member;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XQM on 2017/7/23.
 */

public class MemberAdapter extends SimpleRecAdapter<Member, MemberAdapter.ViewHolder> {

    public MemberAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_member;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        Glide.with(context).load(data.get(position).getHeadUrl()).into(holder.civHead);
        holder.tvName.setText(data.get(position).getName());
        holder.tvGrade.setText(data.get(position).getGrade());
        holder.tvGroup.setText(data.get(position).getGroup());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, data.get(position), 0, holder);
                }
            }
        });
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_head)
        CircleImageView civHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_grade)
        TextView tvGrade;
        @BindView(R.id.tv_group)
        TextView tvGroup;
        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
