package com.jidouauto.ui.oushang.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;


public class SimpleListAdapter extends RecyclerView.Adapter {
    private String[] dataSource;
    private SimpleListDialog.ItemClickListener itemClickListener;

    public SimpleListAdapter(String[] dataSource, SimpleListDialog.ItemClickListener itemClickListener) {
        this.dataSource = dataSource;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_simple_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).assign(dataSource[i]);
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }

        public void assign(final String item) {
            textView.setText(item);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.click(getAdapterPosition(), item);
                    }
                }
            });
        }
    }
}
