package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.ftkj.customview_test.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by FTKJ on 2017/5/26.
 */

public class MyCustomView extends View {
    private int textSize;
    private int textColor;
    private String textText;
    private Paint mPaint;
    private Rect mBound;

    public MyCustomView(Context context) {
        this(context, null);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCustomView, defStyleAttr, 0);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MyCustomView_titleSize:
                    textSize = a.getDimensionPixelSize(attr, DensityUtil.sp2px(context, 16));
                    break;
                case R.styleable.MyCustomView_titleColor:
                    textColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.MyCustomView_titleText:
                    textText = a.getString(attr);
                    break;
            }
        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(textSize);

        mBound = new Rect();
        mPaint.getTextBounds(textText, 0, textText.length(), mBound);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        Rect mBouns = new Rect();
        if (widthmodel == MeasureSpec.EXACTLY) {
            width = widthsize;
        } else {
            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(textText, 0,textText.length(),mBouns);
            width = ((int) (mBouns.width() + getPaddingLeft() + getPaddingRight()));
        }

        if (heightmodel == MeasureSpec.EXACTLY) {
            height = heightsize;
        } else {

            mPaint.setTextSize(textSize);
            mPaint.getTextBounds(textText, 0, textText.length(), mBouns);
            height = mBouns.height()+getPaddingTop()+getPaddingBottom();

        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getMeasuredWidth()+mBound.width()/2, getMeasuredHeight()+mBound.height()/2, mPaint);
        mPaint.setColor(textColor);
        canvas.drawText(textText, getWidth() / 2 - mBound.width() / 2,getHeight() / 2+mBound.height()/4, mPaint);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                textText = randomText();
                postInvalidate();
            }
        });

    }


    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }
}
