package com.example.ftkj.customview_test.customview.weekview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by FTKJ on 2017/11/10.
 */

public class WeekView extends ViewGroup {

    private Scroller mScroller;
    private int mTouchSlop;
    private float mXDown;
    private float mXMove;
    private float mXLastMove;
    private int leftBorder;//左边界

    private int rightBorder;//右边界
    private int mDistance;

    private final int Mynumber = 5;
    private ItemOnclickListener mItemOnclickListener;
    private int selecedPosition;

    public WeekView(Context context) {
        this(context, null);
    }

    public WeekView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * init
         */
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int fifthWidth = getWidth() / Mynumber;
        mDistance = (fifthWidth - getChildAt(0).getMeasuredWidth()) / 2;
        int centerVertical = (getHeight() - getChildAt(0).getMeasuredHeight()) / 2;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (i * 2 + 1 == Mynumber) {
                startAnim(childView, 1.0f, 1.4f);
                selecedPosition = i;
            }
            childView.layout(i * fifthWidth + mDistance, centerVertical, (i + 1) * fifthWidth - mDistance, centerVertical + childView.getMeasuredHeight());
            childView.setOnClickListener(new MyItemOnclickListener(i));
        }
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childenHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(0);

            childenHeight = (int) (childView.getMeasuredHeight() * 1.5);
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, childenHeight);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                if (Math.abs(mXMove - mXDown) > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getRight() > getScrollX() + getWidth() / 2 && childView.getLeft() < getScrollX() + getWidth() / 2) {
                startAnim(childView, 1.0f, 1.4f);
                selecedPosition = i;
            } else {
                startAnim(childView, 1.0f, 1.0f);
            }
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                if (scrolledX + getScrollX() <= 0) {
                    scrollTo(0, 0);
                    return true;
                } else if (scrolledX + getScrollX() + getWidth() > rightBorder + mDistance) {
                    scrollTo(rightBorder + mDistance - getWidth(), 0);
                    return true;
                }
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public void startAnim(View childView, float priviceScale, float afterScale) {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(childView, "scaleX", priviceScale, afterScale);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(childView, "scaleY", priviceScale, afterScale);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200);
        animatorSet.playTogether(anim1, anim2);
        animatorSet.start();
    }

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        mItemOnclickListener = itemOnclickListener;
    }

    public void startAnimByClick(int position) {
        if (selecedPosition == position) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (i == position) {
                startAnim(childView, 1.0f, 1.4f);
            } else if (i == selecedPosition) {
                startAnim(childView, 1.0f, 1.0f);
            }
        }
        int deX = (int) ((position - selecedPosition) * (1.2f * getChildAt(0).getMeasuredWidth() + mDistance));
        mScroller.startScroll(getScrollX(), 0, deX, 0, 500);
        selecedPosition = position;
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    class MyItemOnclickListener implements OnClickListener {

        private int mPosition;

        public MyItemOnclickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            mItemOnclickListener.onClick(v, mPosition);
        }
    }
}
