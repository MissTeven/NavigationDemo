package com.jidouauto.ui.oushang.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface IDialogFactory {
    /**
     * @param context
     * @param contentView 自定义的内容视图
     * @return
     */
    BaseDialog getDialog(Context context, @NonNull View contentView);

    BaseDialog getTipDialog(Context context, int resTipId);
    BaseDialog getTipDialog(Context context, String resTip);

    BaseDialog getListDialog(Context context, String[] dataSource,SimpleListDialog.ItemClickListener itemClickListener);

    BaseDialog getListDialog(Context context, RecyclerView.Adapter adapter);
}
