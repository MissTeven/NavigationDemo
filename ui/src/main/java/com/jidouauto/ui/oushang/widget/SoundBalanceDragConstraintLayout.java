package com.jidouauto.ui.oushang.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SoundBalanceDragConstraintLayout extends ConstraintLayout {
    private static final String TAG = "DragConstraintLayout";

    private View mDragView;
    private ViewDragHelper mViewDragHelper;
    private int mDragViewMarginSide;
    private int mDragViewMarginBottom;
    private Point mDragPoint;

    public SoundBalanceDragConstraintLayout(Context context) {
        this(context, null);
    }

    public SoundBalanceDragConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoundBalanceDragConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDragHelper();
    }


    private void initViewDragHelper() {
        mViewDragHelper = ViewDragHelper.create(this, 10.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBorder = getPaddingLeft();
                int rightBorder = getWidth() - leftBorder - child.getWidth();
                return Math.min(Math.max(leftBorder, left), rightBorder);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int topBorder = getPaddingTop();
                int bottomBorder = getHeight() - topBorder - child.getHeight();
                return Math.min(Math.max(topBorder, top), bottomBorder);
            }

            /**
             * 该view水平方向拖动的范围(子控件消耗点击事件时候才回调（例如：按钮）)
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            /**
             * 该view垂直方向拖动的范围(子控件消耗点击事件时候才回调（例如：按钮）)
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.d(TAG, "onViewReleased: " + "xvel=>" + xvel + "yvel=>" + yvel);
                if (mDragPoint == null) {
                    mDragPoint = new Point();
                }
                if (releasedChild == mDragView) {
                    if (mDragView.getLeft() <= mDragViewMarginSide) {
                        mDragPoint.x = mDragViewMarginSide;
                    } else if (mDragView.getLeft()+mDragView.getWidth() >= getWidth() - mDragViewMarginSide) {
                        mDragPoint.x = getWidth() - mDragViewMarginSide - mDragView.getWidth();
                    }else {
                        mDragPoint.x = mDragView.getLeft();
                    }

                    if (mDragView.getBottom() > getHeight() - mDragViewMarginBottom) {
                        mDragPoint.y = getHeight() - mDragViewMarginBottom - mDragView.getHeight();
                    } else {
                        mDragPoint.y = mDragView.getTop();
                    }
                    mViewDragHelper.settleCapturedViewAt(mDragPoint.x, mDragPoint.y);
                    invalidate();
                }
                super.onViewReleased(releasedChild, xvel, yvel);
            }
        });

        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getTag() != null && child.getTag().equals("dragView")) {
                mDragView = child;
                break;
            }
        }
        mDragViewMarginSide = ((LayoutParams) mDragView.getLayoutParams()).leftMargin > 0 ? ((LayoutParams) mDragView.getLayoutParams()).leftMargin : ((LayoutParams) mDragView.getLayoutParams()).rightMargin;
        mDragViewMarginBottom = ((LayoutParams) mDragView.getLayoutParams()).bottomMargin;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mDragPoint != null) {
            mDragView.layout(mDragPoint.x, mDragPoint.y, mDragPoint.x + mDragView.getWidth(), mDragPoint.y + mDragView.getHeight());
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}

