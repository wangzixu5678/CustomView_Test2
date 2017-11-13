package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by FTKJ on 2017/11/2.
 */

public class MyTabLayout extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private List<String> mData;
    private onItemClick mItemClick;
    private Context mContext;
    private boolean mOnce;
    public MyTabLayout(Context context) {
        this(context, null);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * init()
         */
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.GREEN);
        mContext = context;

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    private void initItem(Context context) {
        for (int i = 0; i < mData.size(); i++) {
            TextView textView = new TextView(context);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClick.itemClick(finalI,mData.get(finalI),v);
                }
            });
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setGravity(Gravity.CENTER);
            textView.setText(mData.get(i));
            addView(textView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }

    public void addData(List<String> datas) {
        mData = datas;
    }

    public void setOnItemClickListener(onItemClick itemClick){
        mItemClick = itemClick;
    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce){
            initItem(mContext);
            mOnce = true;
        }

    }
    public interface onItemClick{
        void itemClick(int position,String data,View view);
    }




}
