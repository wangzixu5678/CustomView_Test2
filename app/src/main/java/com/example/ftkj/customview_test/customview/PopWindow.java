package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by FTKJ on 2017/6/1.
 */

public class PopWindow extends View {
    //画笔
    private Paint mPaint;
    //百分比
    private double mPrecent =0.8;


    //气泡矩形的宽
    private int mRectWidth;


    //气泡矩形的高
    private int mRectHeight;

    private Paint textPaint;


    private String textString="大家好";
    private Rect mTextRect;

    public PopWindow(Context context) {
        this(context,null);
    }

    public PopWindow(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public PopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(DensityUtil.sp2px(context,20));

        mTextRect = new Rect();
        textPaint.getTextBounds(textString,0,textString.length(),mTextRect);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthModel==MeasureSpec.EXACTLY){
            mRectWidth = (int) (widthSize*mPrecent);
        }
        if (heightModel==MeasureSpec.EXACTLY){
            mRectHeight = (int) (heightSize*mPrecent+0.1);
        }
       setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(0,0,mRectWidth,mRectHeight),10,10,mPaint);
        Path path = new Path();
        path.moveTo(mRectWidth/2-30,mRectHeight);
        path.lineTo(mRectWidth/2,mRectHeight+20);
        path.lineTo(mRectWidth/2+30,mRectHeight);
        path.close();
        canvas.drawPath(path,mPaint);
        canvas.drawText(textString,mTextRect.left,Math.abs(mTextRect.top),textPaint);
        super.onDraw(canvas);
    }

    public void setText(String text){
        textString = text;
        invalidate();
    }
}
