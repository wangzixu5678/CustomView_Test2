package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.ftkj.customview_test.R;

/**
 * Created by FTKJ on 2017/6/1.
 */

public class WaveView extends View {
    //浪花边的个数
    private int mWaveCount;
    //每个浪花的宽度
    private int mWaveWidth;

    private float mWaveHeight;
    //浪花的颜色
    private int mWaveColor;
    //圆角还是尖角
    private int mWaveModel;

    //View的高度
    private float mRectHeight;
    //View的宽度
    private float mRectWidth;

    private int mHeight;
    private int mWidth;

    private Context mContext;
    private Paint mPaint;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaveView, defStyleAttr, 0);
        mWaveCount = typedArray.getInt(R.styleable.WaveView_waveCount, 10);
        mWaveWidth = typedArray.getInt(R.styleable.WaveView_waveWidth, 20);
        mWaveColor = typedArray.getColor(R.styleable.WaveView_android_color, Color.parseColor("#2C97DE"));
        mWaveModel = typedArray.getInteger(R.styleable.WaveView_mode, -2);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mWaveColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmodel == MeasureSpec.EXACTLY) {
            mWidth = widthsize;
            mRectWidth = (float) (mWidth * 0.8);
        } else if (widthmodel == MeasureSpec.AT_MOST) {
            mWidth = DensityUtil.dip2px(mContext,300);
            mRectWidth = (float) (mWidth * 0.8);
        }

        if (heightmodel == MeasureSpec.EXACTLY) {
            mHeight = heightsize;
            mRectHeight = (float) (mHeight * 0.8);
        } else if (heightmodel == MeasureSpec.AT_MOST) {
            mHeight = DensityUtil.dip2px(mContext,200);
            mRectHeight = (float) (mHeight * 0.8);
        }



        setMeasuredDimension(mHeight, mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       float padding =((mWidth-mRectWidth)/2);
        canvas.drawRect(new RectF(padding,padding,mWidth-padding, mHeight-padding), mPaint);
        Path path = new Path();
        mWaveHeight = mRectHeight/mWaveCount;
        switch (mWaveModel){
            case -1:
                for (int i = 0; i < mWaveCount; i++) {
                    path.addCircle(padding,mWaveHeight*i+padding+mWaveHeight/2,mWaveHeight/2,Path.Direction.CCW);
                }
                canvas.drawPath(path,mPaint);


                for (int i = 0; i < mWaveCount; i++) {
                    path.addCircle(padding+mRectWidth,mWaveHeight*i+padding+mWaveHeight/2,mWaveHeight/2,Path.Direction.CCW);
                }
                canvas.drawPath(path,mPaint);
                break;
            case -2:
                path.moveTo( padding, padding);
                for (int i = 0; i < mWaveCount; i++) {
                    path.lineTo( padding-mWaveWidth, padding+i*mWaveHeight+(mWaveHeight/2));
                    path.lineTo( padding, padding+(i+1)*mWaveHeight);
                    Log.d("AAAA", "onDraw:"+( padding+(i+1)*mWaveHeight)  +"mRectHeight"+mRectHeight);
                }
                path.close();
                canvas.drawPath(path,mPaint);
                path.moveTo(padding+mRectWidth,padding);
                for (int i = 0; i < mWaveCount; i++) {
                    path.lineTo(padding+mRectWidth+mWaveWidth,padding+mWaveHeight/2+i*mWaveHeight);
                    path.lineTo(padding+mRectWidth,padding+(i+1)*mWaveHeight);
                }
                path.close();
                canvas.drawPath(path,mPaint);
                break;
        }

        super.onDraw(canvas);
    }
}
