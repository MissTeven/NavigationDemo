package com.jidouauto.ui.oushang.tip;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;

public class BaseTip implements ITip {
    private static final String TAG = "BaseTip";
    private Context context;
    /**
     * tipView的Tag，为了方便查找tipView
     */
    private String viewTag = TAG;
    /**
     * tip的图标资源
     */
    private int iconRes = R.drawable.loading;
    /**
     * 显示时长
     */
    private int showTimeLength = -1;
    /**
     * tip的内容
     */
    private String tipContent = "";

    /**
     * 外围区域是否可以点击
     */
    private boolean outsideClickable = true;

    private Rect marginRect;

    BaseTip(Context context) {
        this.context = context;
    }

    @Override
    public void show() {
        FrameLayout decorView = (FrameLayout) ((Activity) context).getWindow().getDecorView();
        View tipView = decorView.findViewWithTag(viewTag);
        if (tipView == null) {
            View tipCore = LayoutInflater.from(context).inflate(R.layout.layout_tip, decorView, false);
            if (outsideClickable) {
                tipView = tipCore;
            } else {
                tipView = new FrameLayout(getContext());
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                tipView.setLayoutParams(lp);

                FrameLayout.LayoutParams coreLp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                coreLp.gravity = Gravity.CENTER;
                tipCore.setLayoutParams(coreLp);
                ((FrameLayout) tipView).addView(tipCore);

                tipView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
            tipView.setTag(viewTag);
        }
        setConfig(tipView);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tipView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
        }

        if (marginRect != null) {
            View tipContentView = tipView.findViewById(R.id.ll_tip_content);
            FrameLayout.LayoutParams tipContentLp = (FrameLayout.LayoutParams) tipContentView.getLayoutParams();
            tipContentLp.setMargins(marginRect.left, marginRect.top, marginRect.right, marginRect.bottom);
            tipContentView.setLayoutParams(tipContentLp);
        }
        if (decorView.indexOfChild(tipView) == -1) {
            decorView.addView(tipView, layoutParams);
        }
        tipView.setVisibility(View.VISIBLE);

        if (showTimeLength > 0) {
            tipView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hide();
                }
            }, showTimeLength);
        }
    }

    private void setConfig(View tipView) {
        final ImageView iv_icon = tipView.findViewById(R.id.iv_icon);
        iv_icon.setImageDrawable(getContext().getApplicationContext().getResources().getDrawable(iconRes));
        iv_icon.post(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = iv_icon.getDrawable();
                if (drawable instanceof AnimationDrawable) {
                    ((AnimationDrawable) drawable).start();
                }
            }
        });

        TextView tv_title = tipView.findViewById(R.id.tv_title);
        tv_title.setText(tipContent);
    }

    @Override
    public void hide() {
        ViewGroup decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        View tipView = decorView.findViewWithTag(viewTag);
        if (tipView != null) {
            decorView.removeView(tipView);

            ImageView iv_icon = tipView.findViewById(R.id.iv_icon);
            Drawable drawable = iv_icon.getDrawable();
            if (drawable instanceof AnimationDrawable) {
                ((AnimationDrawable) drawable).stop();
            }
            iv_icon.setImageDrawable(null);
        }
    }

    public Context getContext() {
        return context;
    }

    public BaseTip setIconRes(int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public BaseTip setTipContent(String tipContent) {
        this.tipContent = tipContent;
        return this;
    }

    public BaseTip setTipContent(int tipContentRes) {
        this.tipContent = getContext().getString(tipContentRes);
        return this;
    }

    public BaseTip setViewTag(String viewTag) {
        this.viewTag = viewTag;
        return this;
    }

    /**
     * @param marginRect
     * @return 自定义实现位置偏移
     */
    public BaseTip setMargin(Rect marginRect) {
        this.marginRect = marginRect;
        return this;
    }

    /**
     * @param showTimeLength 显示时长，以微秒为单位，当时长超出自动隐藏
     * @return
     */
    public BaseTip setShowTimeLength(int showTimeLength) {
        this.showTimeLength = showTimeLength;
        return this;
    }

    /**
     * @param outsideClickable
     * @return 设置外围区域是否可以点击
     */
    public BaseTip setOutsideClickable(boolean outsideClickable) {
        this.outsideClickable = outsideClickable;
        return this;
    }
}
