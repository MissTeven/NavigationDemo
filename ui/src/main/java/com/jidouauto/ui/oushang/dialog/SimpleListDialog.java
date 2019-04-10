package com.jidouauto.ui.oushang.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public class SimpleListDialog extends ListDialog {
    public interface ItemClickListener {
        void click(int position, String item);
    }

    private String[] dataSource;
    private ItemClickListener itemClickListener;

    SimpleListDialog(Context context, String[] dataSource, ItemClickListener itemClickListener) {
        super(context);
        this.dataSource = dataSource;
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new SimpleListAdapter(dataSource, new ItemClickListener() {
            @Override
            public void click(int position, String item) {
                hide();
                if (itemClickListener != null) {
                    itemClickListener.click(position, item);
                }
            }
        });
    }
}
