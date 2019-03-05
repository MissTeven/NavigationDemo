package com.oaksdance.navigationdemo.ui.fragment.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;

/**
 * 测试跨级KeepStateNavigator下的fragment的生命周期
 */
public class TestCrossLevelContainerFragment extends BaseFragment {

    public static TestCrossLevelContainerFragment getInstance() {
        return new TestCrossLevelContainerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_container, container, false);
    }
}