package com.example.ftkj.customview_test.customview.fiveRings;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.ftkj.customview_test.R;
import com.example.ftkj.customview_test.customview.DensityUtil;

/**
 * Created by FTKJ on 2017/6/19.
 */

public class ColorCircleView extends View {
    private Context mContext;
    private int mRadius;
    private int mStrokewidth;
    private int mColor;
    private Paint mPaint;
    private int mHeight;
    private int mWidth;

    public ColorCircleView(Context context) {
        this(context,null);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorCircleView);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.ColorCircleView_radius, DensityUtil.dip2px(context, 20));
        mStrokewidth = typedArray.getDimensionPixelSize(R.styleable.ColorCircleView_stroke, DensityUtil.dip2px(mContext, 5));
        mColor = typedArray.getColor(R.styleable.ColorCircleView_color, Color.BLACK);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokewidth);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmodel==MeasureSpec.AT_MOST){
            mWidth = mRadius*3;
        }else {
            mWidth = widthsize;
        }

        if (heightmodel==MeasureSpec.AT_MOST){
            mHeight = mRadius*3;
        }else {
            mHeight = heightsize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth/2,mHeight/2,mHeight/2-mStrokewidth,mPaint);
    }
}
