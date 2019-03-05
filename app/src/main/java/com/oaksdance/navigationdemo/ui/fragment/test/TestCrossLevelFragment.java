package com.oaksdance.navigationdemo.ui.fragment.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;

import androidx.navigation.fragment.NavHostFragment;

public class TestCrossLevelFragment extends BaseFragment {

    public static TestCrossLevelFragment getInstance() {
        return new TestCrossLevelFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_take_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(TestCrossLevelFragment.this).navigate(R.id.action_testFragment_to_netContainerFragment);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}