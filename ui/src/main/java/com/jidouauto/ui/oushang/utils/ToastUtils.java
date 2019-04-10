package com.jidouauto.ui.oushang.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.jidouauto.ui.oushang.toast.JToast;

public class ToastUtils {
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static JToast mToast;

    public static void showToast(final Context context, final String text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                if (null == mToast) {
                    mToast = new JToast(context);
                }
                mToast.show(text);
            }
        });
    }
}
