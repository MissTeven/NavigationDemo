package com.jidouauto.ui.oushang.tip;

import android.content.Context;

import com.jidouauto.ui.oushang.R;

public class SuccessTip extends BaseTip {
    private static final String TAG = "SuccessTip";

    public SuccessTip(Context context) {
        super(context);
        setViewTag(TAG);
        setIconRes(R.mipmap.ic_success_tip);
        setTipContent(R.string.complete);
    }
}
