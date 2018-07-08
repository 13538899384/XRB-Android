package com.ygip.xrb_android.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.ygip.xrb_android.R;
import com.ygip.xrb_android.util.DensityUtils;

/**
 * Created by XQM on 2017/9/13.
 */

public class HorizontalProgressBar extends ProgressBar {

    private final int DEFAULT_TEXT_SIZE = 10;
    private final int DEFAULT_TEXT_COLOR = R.color.C2;
    private final int DEFAULT_COLOR_UNREACH = DEFAULT_TEXT_COLOR;
    private final int DEFAULT_HEIGHT_UNREACH = 2;
    private final int DEFAULT_COLOR_REACH = R.color.C0;
    private final int DEFAULT_HEIGHT_REACH = 2;
    private final int DEFAULT_TEXT_OFFSET = 2;

    protected int mTextSize = DensityUtils.sp2px(getContext(),DEFAULT_TEXT_SIZE);
    protected int mTextColor = DEFAULT_TEXT_COLOR;
    protected int mTextOffset = DensityUtils.dip2px(getContext(),DEFAULT_TEXT_OFFSET);
    protected int mUnReachColor = DEFAULT_COLOR_UNREACH;
    protected int mReachColor = DEFAULT_COLOR_REACH;
    protected int mUnReachHeight = DensityUtils.dip2px(getContext(),DEFAULT_HEIGHT_UNREACH);
    protected int mReachHeight = DensityUtils.dip2px(getContext(),DEFAULT_HEIGHT_REACH);

    protected Paint mPaint = new Paint();

    private int mRealWidth;


    public HorizontalProgressBar(Context context) {
        this(context,null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
         obtainStyledAttrs(attrs);
    }

    private void obtainStyledAttrs(AttributeSet attrs) {
        TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs,R.styleable.HorizontalProgressBar);

        mTextSize = (int) mTypedArray.getDimension(R.styleable.HorizontalProgressBar_progress_text_size,mTextSize);
        mTextColor = mTypedArray.getColor(R.styleable.HorizontalProgressBar_progress_text_color,mTextColor);
        mTextOffset = (int) mTypedArray.getDimension(R.styleable.HorizontalProgressBar_progress_text_offset,mTextOffset);
        mReachColor = mTypedArray.getColor(R.styleable.HorizontalProgressBar_progress_reach_color,mReachColor);
        mUnReachColor = mTypedArray.getColor(R.styleable.HorizontalProgressBar_progress_unreach_color,mUnReachColor);
        mReachHeight = (int) mTypedArray.getDimension(R.styleable.HorizontalProgressBar_progress_reach_height,mReachHeight);
        mUnReachHeight = (int) mTypedArray.getDimension(R.styleable.HorizontalProgressBar_progress_unreach_height,mUnReachHeight);

        mTypedArray.recycle();

        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        //确定view的高
        setMeasuredDimension(widthSize,height);

        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

        boolean noNeedUnRech = false;//控制是否绘制

        //draw reach bar
        String text = getProgress() + "%";
        int textWidth = (int) mPaint.measureText(text);


        float radio = getProgress() * 1.0f/getMax();
        float progressX = radio * mRealWidth;

        if (progressX + textWidth > mRealWidth){
            progressX = mRealWidth - textWidth;
            noNeedUnRech = true;
        }
        float endX = progressX - mTextOffset/2;
        if (endX > 0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mRealWidth);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw Text
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent() + mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);

        //draw unreach bar
        if (!noNeedUnRech){
            float start = progressX + mTextOffset/2 +textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY){//等于精确值
            result = size;
        }else {
            int mTextHeight = (int) (mPaint.descent()-mPaint.ascent());
            result = getPaddingTop() + getPaddingBottom() + Math.max(Math.max(mReachHeight,mUnReachHeight),Math.abs(mTextHeight));

            if (mode == MeasureSpec.AT_MOST){//测量值不能超过指定的size
                result = Math.min(result,size);
            }
        }
        return result;
    }
}
