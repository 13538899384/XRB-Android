package com.ygip.xrb_android.comm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.ygip.xrb_android.R;

import com.ygip.xrb_android.helper.CrashModel;

import java.text.SimpleDateFormat;

public class CrashActivity extends AppCompatActivity {
    public static final String CRASH_MODEL = "crash_model";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        CrashModel model = getIntent().getParcelableExtra(CRASH_MODEL);
        if (model == null) {
            return;
        }
        model.getEx().printStackTrace();

        TextView tv_packageName = (TextView) findViewById(R.id.tv_packageName);
        TextView textMessage = (TextView) findViewById(R.id.textMessage);
        TextView tv_className = (TextView) findViewById(R.id.tv_className);
        TextView tv_methodName = (TextView) findViewById(R.id.tv_methodName);
        TextView tv_lineNumber = (TextView) findViewById(R.id.tv_lineNumber);
        TextView tv_exceptionType = (TextView) findViewById(R.id.tv_exceptionType);
        TextView tv_fullException = (TextView) findViewById(R.id.tv_fullException);
        TextView tv_time = (TextView) findViewById(R.id.tv_time);
        TextView tv_model = (TextView) findViewById(R.id.tv_model);
        TextView tv_brand = (TextView) findViewById(R.id.tv_brand);
        TextView tv_version = (TextView) findViewById(R.id.tv_version);
//
        tv_packageName.setText("包名："+model.getClassName());
        textMessage.setText("崩溃信息："+model.getExceptionMsg());
        tv_className.setText("类名："+model.getFileName());
        tv_methodName.setText("方法名："+model.getMethodName());
        tv_lineNumber.setText("行数："+String.valueOf(model.getLineNumber()));
        tv_exceptionType.setText("类型："+model.getExceptionType());
        tv_time.setText("时间："+df.format(model.getTime()));

        tv_model.setText("设备名称："+model.getDevice().getModel());
        tv_brand.setText("设备厂商："+model.getDevice().getBrand());
        tv_version.setText("系统版本："+model.getDevice().getVersion());
        tv_fullException.setText("全部信息："+model.getFullException());

    }
}
