package com.jidouauto.ui.oushang.sideBar;

import java.util.List;

public interface IMenuFilter {
    /**
     * @param dataSource
     * @return 根据用户条件隐藏部分菜单
     */
    List<Menu> hideAmong(List<Menu> dataSource);
}
