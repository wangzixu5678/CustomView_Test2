package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by FTKJ on 2017/6/2.
 */

public class CustomImgContainer extends ViewGroup {
    public CustomImgContainer(Context context) {
        this(context, null);
    }

    public CustomImgContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childHeight = childView.getMeasuredHeight();
            int childWidth = childView.getMeasuredWidth();

            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            int cl = 0, ct = 0, cr = 0,cb = 0;
            switch (i){
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth()-childWidth-cParams.rightMargin-cParams.leftMargin;
                    ct =cParams.topMargin;
                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - childHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth()-childWidth-cParams.rightMargin-cParams.leftMargin;
                    ct = getHeight() - childHeight - cParams.topMargin - cParams.bottomMargin;
                    break;
            }
            cr = cl+childWidth;
            cb = ct+childHeight;


            childView.layout(cl,ct,cr,cb);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthmodel = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
        int heightmodel = MeasureSpec.getMode(heightMeasureSpec);
        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        Log.d("AAA", "onLayout: "+getWidth() +"@!@!" +widthsize);
        int childCount = getChildCount();

        // 用于计算左边两个childView的高度  
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值  
        int rHeight = 0;
        // 用于计算上边两个childView的宽度  
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值  
        int bWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            MarginLayoutParams cParams = (MarginLayoutParams) childView.getLayoutParams();
            if (i == 0 || i == 1) {
                tWidth += childWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth += childWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight+= childHeight + cParams.bottomMargin + cParams.topMargin;
            }
            if (i == 1 || i == 3) {
                rHeight += childHeight + cParams.bottomMargin + cParams.topMargin;
            }
        }
        int width = Math.max(tWidth, bWidth);
        int height = Math.max(lHeight, rHeight);

        setMeasuredDimension((widthmodel == MeasureSpec.EXACTLY) ? widthsize
                : width, (heightmodel == MeasureSpec.EXACTLY) ? heightsize
                : height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
