package com.jidouauto.ui.oushang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.jidouauto.ui.oushang.R;

/**
 * 实现点击态 透明度降低为0.8
 */
public class JFrameLayout extends FrameLayout {

    public JFrameLayout(Context context) {
        this(context, null);
    }

    public JFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setBackgroundColor(getContext().getResources().getColor(R.color.color_bg_pressed));
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
