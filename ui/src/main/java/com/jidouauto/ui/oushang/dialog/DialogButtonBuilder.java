package com.jidouauto.ui.oushang.dialog;

import android.view.View;

import com.jidouauto.ui.oushang.R;

public class DialogButtonBuilder {

    public static DialogButtonBuilder builder() {
        DialogButtonBuilder buttonBuilder = new DialogButtonBuilder();
        return buttonBuilder;
    }

    private DialogButtonBuilder() {
    }
    private int position;
    private int textRes;
    private int textSize;
    private int textColorRes;
    private View.OnClickListener onClickListener;

    int getTextRes() {
        return textRes == 0 ? R.string.have_known : textRes;
    }

    public int getTextSize() {
        return textSize == 0 ? R.dimen.text_size_title : textSize;
    }

    int getTextColorRes() {
        return textColorRes == 0 ? (position == 0 ? R.color.colorAccent : R.color.white) : textColorRes;
    }

    View.OnClickListener getOnClickListener() {
        return onClickListener;
    }


    public DialogButtonBuilder setTextRes(int textRes) {
        this.textRes = textRes;
        return this;
    }

    public DialogButtonBuilder setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public DialogButtonBuilder setTextColorRes(int textColorRes) {
        this.textColorRes = textColorRes;
        return this;
    }

    public DialogButtonBuilder setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public DialogButtonBuilder setPosition(int position) {
        this.position = position;
        return this;
    }
}
