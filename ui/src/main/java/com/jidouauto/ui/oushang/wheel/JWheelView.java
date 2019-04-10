package com.jidouauto.ui.oushang.wheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.ui.oushang.R;

/**
 * Created by you on 2017/9/25.
 * 作QQ:86207610
 */

public class JWheelView extends ViewGroup {
    /**
     * 无效的位置
     */
    public static final int IDLE_POSITION = -1;
    /**
     * 垂直与水平布局两种状态
     */
    public static final int WHEEL_VERTICAL = 1;
    public static final int WHEEL_HORIZONTAL = 2;
    /**
     * item color
     */
    private int textColor = Color.WHITE;
    /**
     * 中心item颜色
     */
    private int textColorCenter;
    /**
     * 文本大小
     */
    private float textSize;
    /**
     * item数量
     */
    private int itemCount = 3;
    /**
     * item大小
     */
    private int itemHeight;
    /**
     * 告左或靠右立体时的偏移系数 必须大于0, 默认0.5F
     */
    private float gravityCoefficient = 0.5f;
    /**
     * 布局方向
     */
    private int orientation = WHEEL_VERTICAL;
    /**
     * 对齐方式
     */
    private int gravity = WheelDecoration.GRAVITY_CENTER;

    /**
     * recyclerView
     */
    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    /**
     * wheel 3d
     */
    private WheelDecoration wheelDecoration;
    private WheelAdapter mWheelAdapter;

    private int lastSelectedPosition = IDLE_POSITION;
    private int selectedPosition = IDLE_POSITION;

    public JWheelView(Context context) {
        super(context);
        init(context, null);
    }

    public JWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public JWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.JWheelView);
            itemCount = a.getInt(R.styleable.JWheelView_wheelItemCount, itemCount);
            textColor = a.getColor(R.styleable.JWheelView_wheelTextColor, Color.WHITE);
            textColorCenter = a.getColor(R.styleable.JWheelView_wheelTextColorCenter, Color.WHITE);
            textSize = a.getDimension(R.styleable.JWheelView_wheelTextSize, getResources().getDimensionPixelSize(R.dimen.text_size_content));
            itemHeight = a.getDimensionPixelOffset(R.styleable.JWheelView_wheelItemHeight, getResources().getDimensionPixelSize(R.dimen.wheel_recycler_view_item_height));
            orientation = a.getInt(R.styleable.JWheelView_wheelOrientation, orientation);
            gravity = a.getInt(R.styleable.JWheelView_wheelGravity, WheelDecoration.GRAVITY_CENTER);
            gravityCoefficient = a.getFloat(R.styleable.JWheelView_gravityCoefficient, gravityCoefficient);
            a.recycle();
        }
        if (gravityCoefficient < 0) {
            gravityCoefficient = 0;
        }
        initRecyclerView(context);
    }

    private void initRecyclerView(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        int totolItemSize = (itemCount * 2 + 1) * itemHeight;
        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(orientation == WHEEL_VERTICAL ?
                LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //让滑动结束时都能定到中心位置
        new LinearSnapHelper().attachToRecyclerView(mRecyclerView);
        this.addView(mRecyclerView, WheelUtils.createLayoutParams(orientation, totolItemSize));

        mWheelAdapter = new WheelAdapter(orientation, itemHeight, itemCount);
        wheelDecoration = new SimpleWheelDecoration(mWheelAdapter, gravity, gravityCoefficient, textColor, textColorCenter, textSize);
        mRecyclerView.addItemDecoration(wheelDecoration);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (listener == null || wheelDecoration == null) return;
                if (wheelDecoration.centerItemPosition == IDLE_POSITION || newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                selectedPosition = wheelDecoration.centerItemPosition;
                if (selectedPosition != lastSelectedPosition) {
                    listener.onItemSelected(wheelDecoration.centerItemPosition);
                    lastSelectedPosition = selectedPosition;
                }
            }
        });
        mRecyclerView.setAdapter(mWheelAdapter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (orientation == WHEEL_HORIZONTAL) {//水平布局时,最好固定高度,垂直布局时最好固定宽度
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void measureHorizontal(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            View child = getChildAt(0);
            width = child.getMeasuredWidth() + getPaddingLeft() + getPaddingRight();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = itemHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    private void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
        int width, height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            View child = getChildAt(0);
            height = child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom();
        }
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = itemHeight + getPaddingLeft() + getPaddingRight();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() <= 0) {
            return;
        }
        View child = getChildAt(0);
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();
        int left, top;
        if (orientation == WHEEL_HORIZONTAL) {//水平布局时,最好固定高度,垂直布局时最好固定宽度
            int centerWidth = (getWidth() - getPaddingLeft() - getPaddingRight() - childWidth) >> 1;
            left = getPaddingLeft() + centerWidth;
            top = getPaddingTop();
        } else {
            int centerHeight = (getHeight() - getPaddingTop() - getPaddingBottom() - childHeight) >> 1;
            left = getPaddingLeft();
            top = getPaddingTop() + centerHeight;
        }
        child.layout(left, top, left + childWidth, top + childHeight);
    }

    public void setAdapter(WheelAdapterWrap adapter) {
        this.selectedPosition = -1;
        this.lastSelectedPosition = -1;
        this.mWheelAdapter.adapter = adapter;
        adapter.wheelAdapter = mWheelAdapter;
        this.mWheelAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (mWheelAdapter != null) {
            this.mWheelAdapter.notifyDataSetChanged();
        }
    }


    public void setCurrentItem(int position) {
        layoutManager.scrollToPositionWithOffset(position, 0);
    }

    public int getCurrentItem() {
        return wheelDecoration.centerItemPosition;
    }

    public String getItem(int position) {
        return mWheelAdapter.getItemString(position);
    }

    private OnItemSelectedListener listener;

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * item selected
     */
    public interface OnItemSelectedListener {
        void onItemSelected(int index);
    }

}
