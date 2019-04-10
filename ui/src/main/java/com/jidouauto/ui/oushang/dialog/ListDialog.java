package com.jidouauto.ui.oushang.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jidouauto.ui.oushang.R;

public abstract class ListDialog extends BaseDialog {
    ListDialog(Context context) {
        super(context);
    }

    @Override
    protected View createContentView() {
        RecyclerView contentVIew = new RecyclerView(getContext());
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_list_dialog));
        contentVIew.addItemDecoration(divider);
        contentVIew.setOverScrollMode(View.OVER_SCROLL_NEVER);
        contentVIew.setLayoutManager(new LinearLayoutManager(getContext()));
        contentVIew.setAdapter(getAdapter());
        return contentVIew;
    }

    protected abstract RecyclerView.Adapter getAdapter();
}
