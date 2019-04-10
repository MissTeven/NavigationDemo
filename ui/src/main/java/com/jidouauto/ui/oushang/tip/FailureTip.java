package com.jidouauto.ui.oushang.tip;

import android.content.Context;

import com.jidouauto.ui.oushang.R;

public class FailureTip extends BaseTip {
    private static final String TAG = "FailureTip";

    public FailureTip(Context context) {
        super(context);
        setViewTag(TAG);
        setIconRes(R.mipmap.ic_failure_tip);
        setTipContent(R.string.failure);
    }
}
