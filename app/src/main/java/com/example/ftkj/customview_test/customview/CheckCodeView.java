package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ftkj.customview_test.R;

/**
 * Created by FTKJ on 2017/6/12.
 */

public class CheckCodeView extends View {

    private Context mContext;
    private int mWidth;
    private int mHeight;
    private int mViewWidth;
    private int mViewHeight;
    private Paint mPaint;
    private int mBeforeColor;
    private int mAfterColor;
    private String mBeforeString;
    private boolean isRuning = false;
    private OnClickListener mListener;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==998){
                int arg1 = msg.arg1;
                if (arg1==0){
                    isRuning = false;
                    mBeforeString = "获取验证码";
                }else if (arg1>0){
                    mBeforeString = arg1 + "S";
                }
                invalidate();
            }
            return true;
        }
    });

    public CheckCodeView(Context context) {
        this(context,null);
    }

    public CheckCodeView(Context context,AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckCodeView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckCodeView, defStyleAttr, 0);
        mBeforeColor = typedArray.getColor(R.styleable.CheckCodeView_beforeColor, Color.parseColor("#1f9cf1"));
        mAfterColor = typedArray.getColor(R.styleable.CheckCodeView_afterColor, Color.parseColor("#999999"));
        mBeforeString = typedArray.getString(R.styleable.CheckCodeView_text);
        mViewWidth = typedArray.getDimensionPixelSize(R.styleable.CheckCodeView_viewWidth, DensityUtil.dip2px(mContext,100));
        mViewHeight = typedArray.getDimensionPixelSize(R.styleable.CheckCodeView_viewHeight, DensityUtil.dip2px(mContext,50));

        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthmodel == MeasureSpec.EXACTLY) {
            mWidth = widthsize;
        } else {
            mWidth = mViewWidth;
        }

        if (heightmodel == MeasureSpec.EXACTLY) {
            mHeight = heightsize;
        } else {
            mHeight = mViewHeight;
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        double v = mHeight * 0.1;
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBeforeColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0,0,mWidth,mHeight),15,15,mPaint);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setTextAlign(Paint.Align.LEFT);

        mPaint.setTextSize(DensityUtil.dip2px(mContext,20));
        Rect bounds = new Rect();
        mPaint.getTextBounds(mBeforeString, 0, mBeforeString.length(), bounds);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (mHeight - fontMetrics.bottom + fontMetrics.top)/2 - fontMetrics.top;
        canvas.drawText(mBeforeString,mWidth/2 - bounds.width()/2,baseline,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean ret = false;
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                ret = true;
                if (!isRuning){
                    isRuning = true;
                    mListener.onClick(this);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int second = 5;
                            while (isRuning){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message message = Message.obtain();
                                message.what = 998;
                                message.arg1 = second;
                                mHandler.sendMessage(message);
                                second = second -1;
                            }
                        }
                    }).start();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return ret;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
       mListener = l;
    }
}
