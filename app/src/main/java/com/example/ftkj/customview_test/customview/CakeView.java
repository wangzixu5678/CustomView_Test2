package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by FTKJ on 2017/6/19.
 */

public class CakeView extends View {
    private int mHeight;
    private int mWideth;
    private Context mContext;
    private List<CakeBeen> mBeens;
    private Paint mPaint;
    private float startAngle = 0;
    private String tempStr;

    public CakeView(Context context) {
        this(context,null);
    }
    public CakeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CakeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        ininPaint();
    }

    private void ininPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        if (widthModel==MeasureSpec.EXACTLY){
            mWideth = widthSize;
        }else if (widthModel==MeasureSpec.AT_MOST){
            mWideth = DensityUtil.dip2px(mContext,400);
        }
        if (heightModel==MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else if (heightModel==MeasureSpec.AT_MOST){
            mHeight = DensityUtil.dip2px(mContext,400);
        }
        setMeasuredDimension(mWideth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mBeens.size(); i++) {
            CakeBeen cakeBeen = mBeens.get(i);
            mPaint.setColor(cakeBeen.mColor);
            RectF rectF = new RectF(0,0,(float) 0.5*mWideth,(float) 0.5*mHeight);
            canvas.drawArc(rectF,startAngle,cakeBeen.degree,true,mPaint);
            startAngle = startAngle+cakeBeen.degree;
            canvas.drawRect(((float) (mWideth * 0.55)), ((float) (mHeight * 0.2))+i*20,((float) (mWideth * 0.55))+40,((float) (mHeight * 0.2))+(i+1)*20,mPaint);
            mPaint.setColor(Color.BLACK);
            tempStr = mBeens.get(i).name+String.valueOf(mBeens.get(i).degree/360);
            canvas.drawText(tempStr,((float) (mWideth * 0.55)+50),((float) (mHeight * 0.2))+i*20+10,mPaint);
        }


    }

    public void setData(List<CakeBeen> list){
        float totleVlaue =0;
        for (int i = 0; i < list.size(); i++) {
            float value = list.get(i).value;
            totleVlaue = totleVlaue+value;
        }
        for (int i = 0; i < list.size(); i++) {
            CakeBeen cakeBeen = list.get(i);
            cakeBeen.degree = (cakeBeen.value/totleVlaue)*360;
        }
        mBeens = list;
    }
}
