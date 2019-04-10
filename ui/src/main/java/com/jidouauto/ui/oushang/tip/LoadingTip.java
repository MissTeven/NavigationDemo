package com.jidouauto.ui.oushang.tip;

import android.content.Context;

import com.jidouauto.ui.oushang.R;

public class LoadingTip extends BaseTip {
    private static final String TAG = "LoadingTip";

    public LoadingTip(Context context) {
        super(context);
        setViewTag(TAG);
        setIconRes(R.drawable.loading);
        setTipContent(R.string.loading);
    }
}
