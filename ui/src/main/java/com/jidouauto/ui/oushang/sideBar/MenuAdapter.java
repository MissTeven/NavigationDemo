package com.jidouauto.ui.oushang.sideBar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MenuAdapter";
    private List<Menu> menus;
    private List<Menu> shownMenus;
    private JSideBar.MenuItemSelectListener menuItemSelectListener;
    private IMenuFilter menuFilter;
    private View mHeaderView;
    private final static int TYPE_HEADER = 0;
    private final static int TYPE_ITEM = 1;
    private DeviationCalculator mDeviationCalculator;

    public MenuAdapter(List<Menu> menus) {
        this.menus = new ArrayList<>(menus);
        this.shownMenus = new ArrayList<>();
        mDeviationCalculator = new DeviationCalculator();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (getItemViewType(i) == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_menu, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).assign(shownMenus.get(mHeaderView != null ? i - 1 : i));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (mHeaderView != null && position == 0) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        shownMenus.clear();
        shownMenus.addAll(menus);

        if (menuFilter != null) {
            List<Menu> hiddenMenus = menuFilter.hideAmong(menus);
            if (hiddenMenus != null) {
                shownMenus.removeAll(hiddenMenus);
            }
        }
        return mHeaderView != null ? shownMenus.size() + 1 : shownMenus.size();
    }

    void setMenuItemSelectListener(JSideBar.MenuItemSelectListener menuItemSelectListener) {
        this.menuItemSelectListener = menuItemSelectListener;
    }

    public void setMenuFilter(IMenuFilter menuFilter) {
        this.menuFilter = menuFilter;
    }

    public void addHeader(View headerView) {
        mHeaderView = headerView;
    }

    public void select(Menu menu) {
        if (!menu.isSelected()) {
            for (Menu bean : menus) {
                bean.setSelected(false);
            }
            menu.setSelected(true);
            if (menuItemSelectListener != null) {
                menuItemSelectListener.select(shownMenus.indexOf(menu), menu);
            }
        }
    }

    private int getSelectedPosition() {
        for (Menu bean : shownMenus) {
            if (bean.isSelected()) {
                return shownMenus.indexOf(bean);
            }
        }
        return -1;
    }

    public void select(int position) {
        select(shownMenus.get(position));
    }

    protected class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) itemView.getLayoutParams();
            if (lp == null) {
                lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            itemView.setLayoutParams(lp);
        }
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_menu;
        private View v_bg;

        ItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_menu = itemView.findViewById(R.id.tv_menu);
            v_bg = itemView.findViewById(R.id.v_bg);
            itemView.post(new Runnable() {
                @Override
                public void run() {
                    mDeviationCalculator.setItemHeight(itemView.getHeight());
                }
            });

        }

        void assign(final Menu menu) {
            tv_menu.setText(menu.getTextRes());
            Drawable drawable = createLeftDrawableSelector(itemView.getContext(), menu.getDrawableRes(), menu.getPressedDrawableRes());
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            tv_menu.setCompoundDrawables(drawable, null, null, null);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDeviationCalculator.calculate(getSelectedPosition(), shownMenus.indexOf(menu));
                    itemView.setSelected(true);
                    select(menu);
                }
            });
            itemView.post(new Runnable() {
                @Override
                public void run() {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v_bg.getLayoutParams();
                    if (menu.isSelected()) {
                        lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    } else {
                        lp.setMargins(0, 0, 0, itemView.getHeight());
                    }
                    v_bg.setLayoutParams(lp);
                    itemView.setSelected(menu.isSelected());
                }
            });

            mDeviationCalculator.addOnDeviationCalculatorListener(new DeviationCalculator.OnDeviationCalculatorListener() {
                int itemPosition = shownMenus.indexOf(menu);
                @Override
                public void onCalculated(int position, int top, int bottom, int passedPosition,boolean s2b) {
                    Log.d(TAG, "onCalculated: " + " adapterPosition=>" + shownMenus.indexOf(menu) + " position=>" + position + " top=>" + top + " bottom=>" + bottom + "\n");
                    if (itemPosition != position) {
                        if (s2b) {
                            if (itemPosition<=passedPosition){
                                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v_bg.getLayoutParams();
                                lp.setMargins(0, 0, 0, itemView.getHeight());
                                v_bg.setLayoutParams(lp);
                            }
                        }else {
                            if (itemPosition>=passedPosition){
                                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v_bg.getLayoutParams();
                                lp.setMargins(0, 0, 0, itemView.getHeight());
                                v_bg.setLayoutParams(lp);
                            }
                        }
                        return;
                    }
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v_bg.getLayoutParams();
                    lp.setMargins(0, top, 0, bottom);
                    v_bg.setLayoutParams(lp);
                }

                @Override
                public void startRolling(int startPosition) {
                    if (itemPosition==startPosition){
                        itemView.setSelected(false);
                    }
                }

                @Override
                public void endRolling(int endPosition) {
                    if (itemPosition==endPosition){
                        itemView.setSelected(true);
                    }
                }
            });

            if (shownMenus.size() < menus.size() && menu.getPosition() + 1 == shownMenus.size()) {
                boolean hasSelection = false;
                for (Menu bean : shownMenus) {
                    if (bean.isSelected()) {
                        hasSelection = true;
                        break;
                    }
                }
                if (!hasSelection) {
                    itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            select(menu);
                            notifyDataSetChanged();
                        }
                    });
                }
            }
        }

        /**
         * 设置LeftDrawableSelector。
         */
        private StateListDrawable createLeftDrawableSelector(Context context, int idNormal, int idPressed) {
            StateListDrawable bg = new StateListDrawable();
            Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
            Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
            bg.addState(new int[]{android.R.attr.state_selected}, pressed);
            bg.addState(new int[]{}, normal);
            return bg;
        }
    }
}
