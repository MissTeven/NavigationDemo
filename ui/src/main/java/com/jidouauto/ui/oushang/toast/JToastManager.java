package com.jidouauto.ui.oushang.toast;

import android.content.Context;

public class JToastManager {
    private static JToastManager sJToastManager;
    private JToast mJToast;
    private JToastConfig mJToastConfig;

    private JToastManager() {
    }

    public static JToastManager getInstance() {
        if (sJToastManager == null) {
            sJToastManager = new JToastManager();
        }
        return sJToastManager;
    }

    public void show(Context context) {
        if (mJToast == null) {
            mJToast = new JToast(context);
        }
        if (mJToastConfig != null) {
            mJToast.show(mJToastConfig);
        }
    }

    public JToastManager setToastConfig(JToastConfig JToastConfig) {
        mJToastConfig = JToastConfig;
        return this;
    }
}
