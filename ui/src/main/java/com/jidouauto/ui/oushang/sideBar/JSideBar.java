package com.jidouauto.ui.oushang.sideBar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jidouauto.ui.oushang.R;

import java.util.ArrayList;
import java.util.List;

public class JSideBar extends FrameLayout implements ISideBar {
    public interface MenuItemSelectListener {
        void select(int position, Menu item);
    }

    public interface OnBackClickListener {
        void back();
    }

    private Drawable backIcon;
    private CharSequence title;
    private int menuTextsRes;
    private int menuIconsRes;
    private int menuPressedIconsRes;
    private int mDefaultSelectPosition;
    private RecyclerView.Adapter mMenuAdapter;
    private RecyclerView rv_menu;
    private View mHeaderView;
    private OnBackClickListener mOnBackClickListener = new OnBackClickListener() {
        @Override
        public void back() {
            ((Activity) getContext()).onBackPressed();
        }
    };

    public JSideBar(Context context) {
        this(context, null);
    }

    public JSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_sidebar_content, this, false);

        ImageView iv_cancel = contentView.findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.back();
                }
            }
        });
        TextView tv_title = contentView.findViewById(R.id.tv_title);
        rv_menu = contentView.findViewById(R.id.rv_menu);
        rv_menu.setOverScrollMode(View.OVER_SCROLL_NEVER);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(contentView, lp);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.JSideBar, 0, 0);
        try {
            title = a.getText(R.styleable.JSideBar_titleRes);
            backIcon = a.getDrawable(R.styleable.JSideBar_backRes);
            menuIconsRes = a.getResourceId(R.styleable.JSideBar_menuIconsRes, 0);
            menuPressedIconsRes = a.getResourceId(R.styleable.JSideBar_menuPressedIconsRes, 0);
            menuTextsRes = a.getResourceId(R.styleable.JSideBar_menuTextsRes, 0);
            mDefaultSelectPosition = a.getInt(R.styleable.JSideBar_selectPosition, 0);
        } catch (Exception ex) {
            Log.e(getClass().getSimpleName(), ex.getMessage() + "/" + ex.getCause());
        } finally {
            a.recycle();
        }
        tv_title.setText(title);
        backIcon.setBounds(0, 0, backIcon.getMinimumWidth(), backIcon.getMinimumHeight());
        iv_cancel.setImageDrawable(backIcon);

        List<Menu> menus = new ArrayList<>();
        if (!(menuTextsRes == 0 || menuIconsRes == 0 || menuPressedIconsRes == 0)) {
            TypedArray iconsRes = getResources().obtainTypedArray(menuIconsRes);
            TypedArray selectedIconsRes = getResources().obtainTypedArray(menuPressedIconsRes);
            TypedArray textsRes = getResources().obtainTypedArray(menuTextsRes);

            int size = Math.min(Math.min(iconsRes.length(), selectedIconsRes.length()), textsRes.length());
            for (int i = 0; i < size; i++) {
                Menu menu = new Menu(i, iconsRes.getResourceId(i, -1), selectedIconsRes.getResourceId(i, -1), textsRes.getString(i));
                menus.add(menu);
            }
            if (mDefaultSelectPosition < menus.size()) {
                menus.get(mDefaultSelectPosition).setSelected(true);
            }
            iconsRes.recycle();
            selectedIconsRes.recycle();
            textsRes.recycle();
        }
        mMenuAdapter = new MenuAdapter(menus);
    }

    public ISideBar setMenuItemSelectListener(MenuItemSelectListener menuItemSelectListener) {
        if (mMenuAdapter instanceof MenuAdapter) {
            ((MenuAdapter) mMenuAdapter).setMenuItemSelectListener(menuItemSelectListener);
        }
        return this;
    }

    @Override
    public ISideBar addHeaderView(View view) {
        mHeaderView = view;
        return this;
    }

    @Override
    public ISideBar showMenus() {
        if (mHeaderView != null && (mMenuAdapter instanceof MenuAdapter)) {
            ((MenuAdapter) mMenuAdapter).addHeader(mHeaderView);
        }
        rv_menu.setAdapter(mMenuAdapter);
        rv_menu.setLayoutManager(new LinearLayoutManager(getContext()));
        return this;
    }

    @Override
    public JSideBar setMenuFilter(IMenuFilter menuFilter) {
        if (mMenuAdapter instanceof MenuAdapter) {
            ((MenuAdapter) mMenuAdapter).setMenuFilter(menuFilter);
        }
        return this;
    }

    @Override
    public ISideBar customAdapter(RecyclerView.Adapter adapter) {
        mMenuAdapter = adapter;
        return this;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mMenuAdapter;
    }

    @Override
    public ISideBar selectPosition(int selectPosition) {
        if (mMenuAdapter instanceof MenuAdapter && mMenuAdapter.getItemCount() > selectPosition) {
            ((MenuAdapter) mMenuAdapter).select(selectPosition);
        }
        return this;
    }
    @Override
    public ISideBar setOnBackClickListener(OnBackClickListener onBackClickListener) {
        mOnBackClickListener = onBackClickListener;
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
