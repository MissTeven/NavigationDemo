package com.oaksdance.navigationdemo.ui.fragment.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;
import com.oaksdance.navigationdemo.navigation.KeepStateNavigator;

import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class NetFragment extends BaseFragment {
    private static final String TAG = "NetFragment";
    private NavController navController;
    private int position;

    public static NetFragment getInstance() {
        return new NetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
    }

    @SuppressLint("CutPasteId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View mContentView = inflater.inflate(R.layout.fragment_net, container, false);
        Button btn_wifi = mContentView.findViewById(R.id.btn_wifi);
        Button btn_hot = mContentView.findViewById(R.id.btn_hot);
        btn_wifi.setOnClickListener(v -> {
            position = 0;
            btn_wifi.setActivated(true);
            btn_hot.setActivated(false);
            select(0);
        });
        btn_hot.setOnClickListener(v -> {
            position = 1;
            btn_wifi.setActivated(false);
            btn_hot.setActivated(true);
            select(1);
        });
        // get fragment
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.net_host_fragment);
        // setup custom navigator
        Navigator navigator = new KeepStateNavigator(inflater.getContext(), navHostFragment.getChildFragmentManager(), R.id.net_host_fragment);
        navController = navHostFragment.getNavController();
        navController.getNavigatorProvider().addNavigator(navigator);
        // set navigation graph
        navController.setGraph(R.navigation.net_navigation);

        btn_wifi.setActivated(position == 0);
        btn_hot.setActivated(position == 1);
        return mContentView;
    }

    private void select(int position) {
        navController.navigate(getResId(position));
    }

    private int getResId(int position) {
        if (position == 0) {
            return R.id.wiFiFragment;
        } else {
            return R.id.hotFragment;
        }
    }


    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: ");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}