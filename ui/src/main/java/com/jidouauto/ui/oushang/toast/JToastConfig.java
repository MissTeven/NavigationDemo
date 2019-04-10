package com.jidouauto.ui.oushang.toast;

import android.view.Gravity;

public class JToastConfig {
    public static int GRAVITY_DEFAULT = -1;
    public static int XOFFSET_DEFAULT = 0x123456;
    public static int YOFFSET_DEFAULT = 0x789456;
    private String mText;
    private int mGravity;
    private int mXOffset;
    private int mYOffset;

    public JToastConfig setText(String mText) {
        this.mText = mText;
        return this;
    }


    public String getText() {
        return mText;
    }

    public int getGravity() {
        return mGravity;
    }

    public void setGravity(int gravity) {
        mGravity = gravity;
    }

    public int getXOffset() {
        return mXOffset;
    }

    public void setXOffset(int XOffset) {
        mXOffset = XOffset;
    }

    public void setYOffset(int YOffset) {
        mYOffset = YOffset;
    }

    public int getYOffset() {
        return mYOffset;
    }

    public static class JToastConfigBuilder {
        private String mText;
        private int mGravity = GRAVITY_DEFAULT;
        private int mXOffset = XOFFSET_DEFAULT;
        private int mYOffset = YOFFSET_DEFAULT;

        private JToastConfigBuilder() {
        }

        public static JToastConfigBuilder builder() {
            return new JToastConfigBuilder();
        }

        public JToastConfigBuilder setText(String text) {
            mText = text;
            return this;
        }

        public JToastConfig build() {
            JToastConfig config = new JToastConfig();
            config.setText(mText);
            config.setGravity(mGravity);
            config.setXOffset(mXOffset);
            config.setYOffset(mYOffset);
            return config;
        }

        public JToastConfigBuilder setGravity(int gravity) {
            mGravity = gravity;
            if (mXOffset == XOFFSET_DEFAULT) {
                setXOffset(0);
            }
            if (mYOffset == YOFFSET_DEFAULT) {
                setYOffset(0);
            }
            return this;
        }

        public JToastConfigBuilder setXOffset(int XOffset) {
            mXOffset = XOffset;
            return this;
        }

        public JToastConfigBuilder setYOffset(int YOffset) {
            mYOffset = YOffset;
            return this;
        }
    }
}
