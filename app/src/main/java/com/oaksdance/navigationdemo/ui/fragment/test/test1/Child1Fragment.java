package com.oaksdance.navigationdemo.ui.fragment.test.test1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;
import com.oaksdance.navigationdemo.ui.fragment.net.hot.UpdateNameFragment;

import androidx.navigation.fragment.NavHostFragment;

public class Child1Fragment extends BaseFragment {

    public static Child1Fragment getInstance() {
        return new Child1Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(Child1Fragment.this).navigate(R.id.action_child1Fragment_popUpTo_test1Fragment);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}