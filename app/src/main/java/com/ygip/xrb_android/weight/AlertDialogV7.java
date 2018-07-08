package com.ygip.xrb_android.weight;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.ygip.xrb_android.R;


/**
 * @author zhijian
 * @Description:
 * @date 2017/1/22
 */

public class AlertDialogV7 {

    private Context mContext;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;

    public AlertDialogV7(Context context){
        mContext = context;
        mBuilder = new AlertDialog.Builder(context);
    }

    public AlertDialogV7 setTitle(String title){
        mBuilder.setTitle(title);
        return this;
    }

    public AlertDialogV7 setMsg(String msg){
        mBuilder.setMessage(msg);
        return this;
    }

    public AlertDialogV7 setPosBtn(String str, DialogInterface.OnClickListener listener){
        mBuilder.setPositiveButton(str, listener);
        return this;
    }

    public AlertDialogV7 setNegBtn(String str, DialogInterface.OnClickListener listener){
        mBuilder.setNegativeButton(str, listener);
        return this;
    }

    public AlertDialogV7 show(){
        mAlertDialog = mBuilder.show();
        return this;
    }

    // 一定要给mAlertDialog赋值才能引用下面这个方法
    public AlertDialogV7 setPosBtnTxtCol(){
        if (mAlertDialog == null) return this;
        Button btn_positive = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btn_positive.setTextColor(mContext.getResources().getColor(R.color.C0));
        return this;
    }

    // 一定要给mAlertDialog赋值才能引用下面这个方法
    public AlertDialogV7 setNegBtnTxtCol(){
        if (mAlertDialog == null) return this;
        Button btn_negative = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        btn_negative.setTextColor(mContext.getResources().getColor(R.color.C2));
        return this;
    }
}
