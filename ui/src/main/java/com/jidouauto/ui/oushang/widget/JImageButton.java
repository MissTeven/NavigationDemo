package com.jidouauto.ui.oushang.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.jidouauto.ui.oushang.R;

/**
 * 实现点击态 透明度降低为0.8
 */
public class JImageButton extends AppCompatImageView {
    private boolean isBgTransparent = true;
    private float clickTextAlpha = 0.5f;

    public JImageButton(Context context) {
        this(context, null);
    }

    public JImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.JImageButton, 0, 0);
        try {
            isBgTransparent = a.getBoolean(R.styleable.JImageButton_isBgTransparent, true);
            if (isBgTransparent) setBackgroundColor(Color.TRANSPARENT);
            clickTextAlpha = a.getFloat(R.styleable.JImageButton_clickAlpha, 0.5f);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage() + "/" + ex.getCause());
        } finally {
            a.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setAlpha(clickTextAlpha);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            setAlpha(1.0f);
        }
        return super.onTouchEvent(event);
    }
}
