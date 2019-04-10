package com.jidouauto.ui.oushang.routeBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;
import com.jidouauto.ui.oushang.widget.JFrameLayout;

public class JRouteBar extends JFrameLayout {
    private static final String TAG = "JRouteBar";

    private TextView tv_title;
    private TextView tv_description;

    public JRouteBar(Context context) {
        this(context, null);
    }

    public JRouteBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JRouteBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_router_bar, this, false);
        tv_title = contentView.findViewById(R.id.tv_title);
        tv_description = contentView.findViewById(R.id.tv_description);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.JRouteBar, 0, 0);
        try {
            String title = a.getString(R.styleable.JRouteBar_titleText);
            String description = a.getString(R.styleable.JRouteBar_description);
            setTitle(title);
            setDescription(description);
        } catch (Exception ignored) {
        } finally {
            a.recycle();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(contentView, lp);
    }

    public JRouteBar setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public JRouteBar setTitle(int titleRes) {
        setTitle(getContext().getString(titleRes));
        return this;
    }

    public JRouteBar setDescription(String description) {
        tv_description.setText(description);
        return this;
    }

    public JRouteBar setDescription(int descriptionRes) {
        setDescription(getContext().getString(descriptionRes));
        return this;
    }
}
