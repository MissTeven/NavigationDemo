package com.jidouauto.ui.oushang.wheel;

public abstract class WheelAdapterWrap {
    WheelAdapter wheelAdapter;

    protected abstract int getItemCount();

    protected abstract String getItem(int index);

    public final void notifyDataSetChanged() {
        if (wheelAdapter != null) {
            wheelAdapter.notifyDataSetChanged();
        }
    }
}
