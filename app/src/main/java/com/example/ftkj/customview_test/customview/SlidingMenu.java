package com.example.ftkj.customview_test.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by FTKJ on 2018/1/17.
 */

public class SlidingMenu extends ViewGroup {

    private int mLeftWidth;
    private View mLeftView;
    private View mContentView;
    private float mDownX;
    private float mDownY;
    private Scroller mScroller;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftView = getChildAt(0);
        mContentView = getChildAt(1);
        LayoutParams params = mLeftView.getLayoutParams();
        mLeftWidth = params.width;


    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int leftWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mLeftWidth, MeasureSpec.EXACTLY);
        mLeftView.measure(leftWidthMeasureSpec, heightMeasureSpec);
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSize, heightMeasureSize);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        mLeftView.layout(-mLeftWidth, 0, 0, mLeftView.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                if (Math.abs(moveX - mDownX)>Math.abs(moveY - mDownY)){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                int dX =  (int) (moveX - mDownX+0.5f);


                if (dX<0&&getScrollX()>=0){
                    scrollTo(0,0);
                }else if (getScrollX()-dX<-mLeftView.getMeasuredWidth()){
                    scrollTo(-mLeftView.getMeasuredWidth(),0);
                }else {
                    scrollBy(-dX,0);
                }


                mDownX  = moveX;
                break;
            case MotionEvent.ACTION_UP:
                int midWith = mLeftView.getMeasuredWidth() / 2;
                if (-getScrollX()>=midWith){
                   // scrollTo(-mLeftView.getMeasuredWidth(),0);
                    mScroller.startScroll(getScrollX(),0,-mLeftView.getMeasuredWidth()-getScrollX(),0,300);
                }else {
                   // scrollTo(0,0);
                    mScroller.startScroll(getScrollX(),0,-getScrollX(),0,300);
                }
                invalidate();
                break;

        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),0);
            invalidate();
        }
    }
}
