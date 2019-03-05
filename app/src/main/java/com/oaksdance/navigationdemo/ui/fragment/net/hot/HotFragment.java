package com.oaksdance.navigationdemo.ui.fragment.net.hot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;
import com.oaksdance.navigationdemo.util.NavigationUtil;
import com.jidouauto.ui.oushang.routeBar.JRouteBar;

public class HotFragment extends BaseFragment {
    public static HotFragment getInstance() {
        return new HotFragment();
    }

    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_net_hot, container, false);
            JRouteBar rb_hot_name = mContentView.findViewById(R.id.rb_hot_name);
            JRouteBar rb_hot_password = mContentView.findViewById(R.id.rb_hot_password);
            rb_hot_name.setOnClickListener(v -> {
                NavigationUtil.takeAction(HotFragment.this, R.id.net_container_host_fragment, R.id.action_netContainerFragment_to_updateNameFragment);
            });
            rb_hot_password.setOnClickListener(v -> {
                NavigationUtil.takeAction(HotFragment.this, R.id.net_container_host_fragment, R.id.action_netContainerFragment_to_updatePasswordFragment);
            });
        }
        return mContentView;
    }
}