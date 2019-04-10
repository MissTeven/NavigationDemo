package com.jidouauto.ui.oushang.toast;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jidouauto.ui.oushang.R;

import static com.jidouauto.ui.oushang.toast.JToastConfig.GRAVITY_DEFAULT;
import static com.jidouauto.ui.oushang.toast.JToastConfig.XOFFSET_DEFAULT;
import static com.jidouauto.ui.oushang.toast.JToastConfig.YOFFSET_DEFAULT;

public class JToast {
    private Context mContext;
    private Toast mToast;
    private View mContentView;
    private TextView mTextView;

    public JToast(Context context) {
        mContext = context.getApplicationContext();
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_toast, null);
        mTextView = mContentView.findViewById(R.id.tv_toast);
    }

    public void show(String text) {
        show(JToastConfig.JToastConfigBuilder.builder().setText(text).build());
    }

    public void show(JToastConfig config) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = new Toast(mContext);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(mContentView);
        mTextView.setText(config.getText());

        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(dm.widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        mToast.setGravity(
                config.getGravity() == GRAVITY_DEFAULT ? mToast.getGravity() : config.getGravity(),
                config.getXOffset() == XOFFSET_DEFAULT ? mToast.getXOffset() : config.getXOffset(),
                config.getYOffset() == YOFFSET_DEFAULT ? mToast.getYOffset() : config.getYOffset()
        );
        mContentView.findViewById(R.id.fl_toast).setLayoutParams(params);
        mToast.show();
    }
}
