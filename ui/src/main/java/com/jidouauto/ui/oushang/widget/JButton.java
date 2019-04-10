package com.jidouauto.ui.oushang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.jidouauto.ui.oushang.R;

/**
 * 实现点击态 透明度降低为0.8
 */
public class JButton extends Button {
    private float clickTextAlpha = 0.5f;

    public JButton(Context context) {
        this(context, null);
    }

    public JButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.JButton, 0, 0);
        try {
            clickTextAlpha = a.getFloat(R.styleable.JButton_clickTextAlpha, 0.5f);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage() + "/" + ex.getCause());
        } finally {
            a.recycle();
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            setAlpha(clickTextAlpha);
        } else {
            setAlpha(1.0f);
        }
    }
}
