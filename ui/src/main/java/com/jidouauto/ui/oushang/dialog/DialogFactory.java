package com.jidouauto.ui.oushang.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DialogFactory implements IDialogFactory {
    public static IDialogFactory getInstance() {
        return DialogFactoryHolder.sDialogFactory;
    }

    private static class DialogFactoryHolder {
        private static DialogFactory sDialogFactory = new DialogFactory();
    }

    @Override
    public BaseDialog getDialog(Context context, @NonNull final View contentView) {
        return new BaseDialog(context) {
            @Override
            protected View createContentView() {
                return contentView;
            }
        };
    }

    @Override
    public BaseDialog getTipDialog(Context context, int resTipId) {
        return new TipDialog(context, resTipId);
    }

    @Override
    public BaseDialog getTipDialog(Context context, String resTip) {
        return new TipDialog(context, resTip);
    }

    @Override
    public BaseDialog getListDialog(Context context, String[] dataSource, SimpleListDialog.ItemClickListener itemClickListener) {
        return new SimpleListDialog(context, dataSource, itemClickListener);
    }

    @Override
    public BaseDialog getListDialog(Context context, final RecyclerView.Adapter adapter) {
        return new ListDialog(context) {
            @Override
            protected RecyclerView.Adapter getAdapter() {
                return adapter;
            }
        };
    }
}
