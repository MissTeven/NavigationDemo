package com.oaksdance.navigationdemo.ui.fragment.net.hot;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseRemovableFragment;

import androidx.navigation.fragment.NavHostFragment;

public class UpdatePasswordFragment extends BaseRemovableFragment {
    public static UpdatePasswordFragment getInstance() {
        return new UpdatePasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_net_hot_update_password, container, false);
        content.findViewById(R.id.btn_back).setOnClickListener(v -> {
            NavHostFragment.findNavController(UpdatePasswordFragment.this).navigate(R.id.action_pop_up_from_updatePasswordFragment);
        });
        return content;
    }
}
