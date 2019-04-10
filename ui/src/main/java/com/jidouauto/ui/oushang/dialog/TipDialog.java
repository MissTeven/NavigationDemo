package com.jidouauto.ui.oushang.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;

public class TipDialog extends BaseDialog {
    private String resTip;

    TipDialog(Context context, int resTipId) {
        super(context);
        this.resTip = context.getResources().getString(resTipId);
    }

    TipDialog(Context context, String resTip) {
        super(context);
        this.resTip = resTip;
    }

    @Override
    protected View createContentView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_tip_content, null);
        TextView tv_content = contentView.findViewById(R.id.tv_content);
        tv_content.setText(resTip);
        return contentView;
    }
}
