package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.ftkj.customview_test.R;

import java.util.Locale;

/**
 * Created by FTKJ on 2017/6/1.
 */

public class PanelView extends View {
    private int mWidth;

    private int strokeWidth = 3;

    private int mHeight;

    //刻度宽度
    private int mTikeWidth;
    private Context mContext;

    private Paint mPaint;

    private Paint mSPaint;

    private Paint mTPaint;

    private Paint mLPaint;

    private Paint mRPaint;

    private Paint mMPaint;

    private int mArcColor;

    private int mPrecent;
    private int mTikeCount;
    private int mPointerColor;
    private int mTextSize;
    private String mText;

    public PanelView(Context context) {
        this(context, null);
    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PanelView, defStyleAttr, 0);
        mArcColor = typedArray.getColor(R.styleable.PanelView_arcColor, Color.parseColor("#5FB1ED"));
        mPointerColor = typedArray.getColor(R.styleable.PanelView_pointerColor, Color.parseColor("#C9DEEE"));
        mTikeCount = typedArray.getInt(R.styleable.PanelView_tikeCount, 12);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.PanelView_android_textSize, DensityUtil.sp2px(context, 24));
        //mText = typedArray.getString(R.styleable.PanelView_android_text);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPrecent = 0;
        mText ="进度:0%";
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mArcColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);


        //填充的颜色
        mSPaint = new Paint();
        mSPaint.setAntiAlias(true);
        mSPaint.setColor(mArcColor);
        mSPaint.setStyle(Paint.Style.STROKE);
        mSPaint.setStrokeWidth(40);


        //没有填充的颜色
        mTPaint = new Paint();
        mTPaint.setAntiAlias(true);
        mTPaint.setColor(Color.WHITE);
        mTPaint.setStyle(Paint.Style.STROKE);
        mTPaint.setStrokeWidth(40);

        //区域1
        mLPaint = new Paint();
        mLPaint.setAntiAlias(true);
        mLPaint.setColor(mArcColor);
        mLPaint.setStyle(Paint.Style.STROKE);
        mLPaint.setStrokeWidth(40);
        //区域4
        mRPaint = new Paint();
        mRPaint.setAntiAlias(true);
        mRPaint.setColor(Color.WHITE);
        mRPaint.setStyle(Paint.Style.STROKE);
        mRPaint.setStrokeWidth(40);

        //工具Paint

        mMPaint = new Paint();
        mMPaint.setAntiAlias(true);
        mMPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmodel == MeasureSpec.EXACTLY) {
            mWidth = widthsize;
        } else {
            mWidth = DensityUtil.dip2px(mContext, 200);
        }

        if (heightmodel == MeasureSpec.EXACTLY) {
            mHeight = heightsize;
        } else {
            mHeight = DensityUtil.dip2px(mContext, 200);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制最外面大圆弧
        canvas.drawArc(new RectF(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight - strokeWidth), 145, 250, false, mPaint);

        int margin = DensityUtil.dip2px(mContext,25);
        RectF sencondRectF = new RectF(strokeWidth + margin, strokeWidth + margin, mWidth - (strokeWidth + margin), mHeight - (strokeWidth + margin));
        float percent = mPrecent / 100f;
        float fill = 250 * percent;
        float empty = 250 - fill;


        //绘制区域1
        if (percent == 0) {
            mLPaint.setColor(Color.WHITE);
        }else {
            mLPaint.setColor(mArcColor);
        }
        canvas.drawArc(sencondRectF, 135, 11, false, mLPaint);
        //绘制区域2 填充部分
        canvas.drawArc(sencondRectF, 145, fill, false, mSPaint);

        //绘制区域3 未填充部分
        canvas.drawArc(sencondRectF, 145 + fill, empty, false, mTPaint);

        //绘制区域4
        if (percent == 1) {
            mRPaint.setColor(mArcColor);
        }else {
            mRPaint.setColor(Color.WHITE);
        }
        canvas.drawArc(sencondRectF, 144 + fill + empty, 10, false, mRPaint);

        //绘制小园外圈

        mMPaint.setColor(mArcColor);
        mMPaint.setStrokeWidth(3);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 30, mMPaint);
        //绘制小元内圈
        mMPaint.setColor(Color.WHITE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 10, mMPaint);

        //绘制刻度
        mTikeWidth = 15;
        mMPaint.setColor(mArcColor);
        mMPaint.setStrokeWidth(3);
        canvas.drawLine(mWidth / 2, strokeWidth, mWidth / 2, mTikeWidth, mMPaint);

        float rAngle = 250f / mTikeCount;

        for (int i = 0; i < mTikeCount/2; i++) {
            canvas.rotate(rAngle, mWidth / 2, mHeight / 2);
            canvas.drawLine(mWidth / 2, strokeWidth, mWidth / 2, mTikeWidth, mMPaint);
        }
        canvas.rotate(-rAngle*(mTikeCount/2),mWidth/2,mHeight/2);

        for (int i = 0; i < mTikeCount / 2; i++) {
            canvas.rotate(-rAngle, mWidth / 2, mHeight / 2);
            canvas.drawLine(mWidth / 2, strokeWidth, mWidth / 2, mTikeWidth, mMPaint);
        }
        canvas.rotate(rAngle*(mTikeCount/2),mWidth/2,mHeight/2);

        //绘制指针
        mMPaint.setColor(mPointerColor);
        mMPaint.setStrokeWidth(4);
        canvas.rotate(percent*250-125,mWidth/2,mHeight/2);
        canvas.drawLine(mWidth/2,mHeight/2-5-3,mWidth/2,mHeight/2-80-strokeWidth,mMPaint);
        canvas.rotate(-(percent*250-125),mWidth/2,mHeight/2);

        //绘制矩形

        mMPaint.setStyle(Paint.Style.FILL);
        mMPaint.setColor(mArcColor);

        int rectWidth=60;
        canvas.drawRect(mWidth/2-rectWidth/2,2*mHeight/3,mWidth/2+rectWidth/2,2*mHeight/3+25,mMPaint);
        //绘制文字

        mMPaint.setTextSize(mTextSize);
        mMPaint.setColor(Color.WHITE);
        float textLenght = mMPaint.measureText(mText, 0, mText.length());
        String progress = String.format(Locale.CHINA, "进度:%d", ((int) (percent * 100)));
        canvas.drawText(progress,(mWidth-textLenght)/2,2*mHeight/3+60,mMPaint);
    }


    public void setPrecent(int precent){
        mPrecent = precent;
        invalidate();
    }

}
