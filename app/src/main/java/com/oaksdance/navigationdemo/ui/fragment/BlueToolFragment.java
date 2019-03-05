package com.oaksdance.navigationdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;

public class BlueToolFragment extends BaseFragment {

    public static BlueToolFragment getInstance() {
        return new BlueToolFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blue_tool, container, false);
    }
}