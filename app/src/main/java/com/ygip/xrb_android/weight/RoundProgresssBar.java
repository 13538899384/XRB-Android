package com.ygip.xrb_android.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.DensityUtils;

/**
 * Created by XQM on 2017/9/14.
 */

public class RoundProgresssBar extends HorizontalProgressBar {

    private int radius = DensityUtils.dip2px(getContext(),10);
    private int mMaxPaintWidth;

    public RoundProgresssBar(Context context) {
        this(context,null);
    }

    public RoundProgresssBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgresssBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mReachHeight = (int) (mUnReachHeight * 1.0f);

        TypedArray mTypeArray = context.obtainStyledAttributes(attrs,R.styleable.RoundProgresssBar);
        radius = (int) mTypeArray.getDimension(R.styleable.RoundProgresssBar_radius,radius);

        mTypeArray.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMaxPaintWidth = Math.max(mReachHeight,mUnReachHeight);
        //默认四个padding都是一样的
        int expect = radius*2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();

        //这个方法跟MeasureSpec获取mode跟size是一样的
        int width = resolveSize(expect,widthMeasureSpec);
        int height = resolveSize(expect,heightMeasureSpec);

        int realWidth = Math.max(width,height);
        radius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth)/2;
        setMeasuredDimension(realWidth,realWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent())/2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth/2,getPaddingTop() + mMaxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);
        //draw unreach bar
        mPaint.setColor(getResources().getColor(mUnReachColor));
        mPaint.setStrokeWidth(mUnReachHeight);
        canvas.drawCircle(radius,radius,radius,mPaint);

        //draw reach bar
        mPaint.setColor(getResources().getColor(mReachColor));
        mPaint.setStrokeWidth(mReachHeight);
        float sweepAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,radius*2,radius*2),0,sweepAngle,false,mPaint);

        //draw text
        mPaint.setColor(getResources().getColor(mTextColor));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,radius-textWidth/2,radius-textHeight,mPaint);

        canvas.restore();
    }
}
