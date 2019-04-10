package com.jidouauto.ui.oushang.sideBar;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface ISideBar {
    /**
     * @return 显示菜单
     */
    ISideBar showMenus();

    /**
     * @param menuFilter
     * @return 设置菜单显示过滤器
     */
    ISideBar setMenuFilter(IMenuFilter menuFilter);

    /**
     * @return 获取菜单适配器实例
     */
    RecyclerView.Adapter getAdapter();

    /**
     * @param menuItemSelectListener
     * @return 设置菜单选择监听
     */
    ISideBar setMenuItemSelectListener(JSideBar.MenuItemSelectListener menuItemSelectListener);

    /**
     * @param adapter
     * @return 自定义菜单适配器
     */
    ISideBar customAdapter(RecyclerView.Adapter adapter);

    /**
     * @param view
     * @return 设置菜单头部
     */
    ISideBar addHeaderView(View view);

    /**
     * @param selectPosition
     * @return 指定选择位置
     */
    ISideBar selectPosition(int selectPosition);

    /**
     * @param onBackClickListener
     * @return 设置点击回退监听
     */
    ISideBar setOnBackClickListener(JSideBar.OnBackClickListener onBackClickListener);
}
