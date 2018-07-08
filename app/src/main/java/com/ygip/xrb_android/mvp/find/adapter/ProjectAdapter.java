package com.ygip.xrb_android.mvp.find.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.mvp.find.model.Project;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;

/**
 * Created by lockyluo on 2017/7/28.
 */

public class ProjectAdapter extends SimpleRecAdapter<Project, ProjectAdapter.ViewHolder> {


    public ProjectAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvProjectName.setText(data.get(position).getProjectName());
        holder.tvProjectLeader.setText((data.get(position).getMembers())[0]);
        holder.llProjectitem.setOnClickListener(view -> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemClick(position, data.get(position), 0, holder);
            }
        });
        holder.llProjectitem.setOnLongClickListener(view -> {
            if (getRecItemClick() != null) {
                getRecItemClick().onItemLongClick(position, data.get(position), 0, holder);
            }
            return true;
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_project;
    }


    static public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_project_headimg)
        ImageView ivProjectHeadimg;
        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_project_leader)
        TextView tvProjectLeader;
        @BindView(R.id.ll_projectitem)
        LinearLayout llProjectitem;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

}
