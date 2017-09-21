package com.example.ftkj.customview_test.customview.fiveRings;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.ftkj.customview_test.customview.DensityUtil;

/**
 * Created by FTKJ on 2017/6/19.
 */

public class FiveRingsLayout extends ViewGroup {
    private int mWidth;
    private int mHeight;
    private Context mContext;
    String str1= "同一个世界,同一个梦想";
    String str2 = "One World,One Dream";
    private Paint mPaint;

    public FiveRingsLayout(Context context) {
        this(context,null);
    }

    public FiveRingsLayout(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public FiveRingsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setBackgroundColor(Color.TRANSPARENT);
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //触发所有子View的onMeasure函数去测量宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传递给子View的布局要求
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wMode == MeasureSpec.AT_MOST){
            mWidth = (int) (wSize*0.9);
        }else if (wMode==MeasureSpec.EXACTLY){
            mWidth = wSize;
        }

        if (hMode ==MeasureSpec.AT_MOST){
            mHeight = (int) (hSize*0.9);
        }else if (hMode==MeasureSpec.EXACTLY){
            mHeight = hSize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        View childView;
        int left = 0;
        int top = 0;
        for (int i = 0; i < count; i++) {
             childView = getChildAt(i);
            switch (i){
                case 0:
                    left = 0;
                    top = 0;
                    break;
                case 1:
                    left = childView.getMeasuredWidth()+5;
                    top = 0;
                    break;
                case 2:
                    left = childView.getMeasuredWidth()*2+5;
                    top = 0;
                    break;
                case 3:
                    left = childView.getMeasuredWidth()/2;
                    top = childView.getMeasuredHeight()/2;
                    break;
                case 4:
                    left = childView.getMeasuredWidth()*3/2;
                    top = childView.getMeasuredHeight()/2;
                    break;
            }
            childView.layout(left,top,left+childView.getMeasuredWidth(),top+childView.getMeasuredHeight());
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(str1,(mWidth-mPaint.measureText(str1))/2,getCircleHeight() / 2 * 3 + 60,mPaint);
        canvas.drawText(str2,(mWidth-mPaint.measureText(str2))/2,getCircleHeight() / 2 * 3 + 90,mPaint);
    }

    /**
     * 获得圆环高度
     *
     * @return 圆环高度
     */
    private int getCircleHeight() {
        //5个圆环大小是一样的，这里就直接取第一个了
        View childView = getChildAt(0);
        return childView.getMeasuredHeight();
    }
}
