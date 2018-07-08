package com.ygip.xrb_android.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import com.ygip.xrb_android.R;

/**
 * Created by lockyluo on 2017/8/4.
 * 对话框
 */

public class DialogUtils {
    static AlertDialog dialogChoose;
    public static void dialog(Context context, ViewDelegateByLocky vDelegate){
        ListView myListView = new ListView(context);
        myListView.setDivider(context.getResources().getDrawable(R.color.C9));

        myListView.setDividerHeight(1);
        if (dialogChoose == null) {
            dialogChoose = new AlertDialog.Builder(context).create();
            dialogChoose.setView(myListView);
//            myListView.setAdapter(new SimpleListAdapter() {
//            });
            myListView.setOnItemClickListener((parent, view, position, id) -> {
                vDelegate.toastShort("");
                dialogChoose.dismiss();
            });
        }

        dialogChoose.show();
    }

    public static void dialogFinish(Context context) //返回确认对话框
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setMessage("确认直接返回？");

        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setPositiveButton("确定", (dialog, which) -> {
            // 点击“确认”后的操作
            ((Activity)context).finish();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            // 点击“取消”后的操作
        });

        AlertDialog ad = builder.create();
        ad.show();
    }
}
