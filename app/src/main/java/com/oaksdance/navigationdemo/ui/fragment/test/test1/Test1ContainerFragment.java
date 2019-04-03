package com.oaksdance.navigationdemo.ui.fragment.test.test1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;
import com.oaksdance.navigationdemo.navigation.JFragmentNavigator;
import com.oaksdance.navigationdemo.navigation.JNavigationUtil;
import com.oaksdance.navigationdemo.navigation.KeepStateNavigator;
import com.oaksdance.navigationdemo.ui.fragment.test.TestCrossLevelContainerFragment;
import com.oaksdance.navigationdemo.util.NavigationUtil;

import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class Test1ContainerFragment extends BaseFragment {
    private static final String TAG = "Test1ContainerFragment";
    private View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view =inflater.inflate(R.layout.fragment_test1_container, container, false);
        JNavigationUtil.inject(this,R.id.test1_container_host_fragment,R.navigation.test1_container_navigation);
        return view;
    }


    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: " + System.currentTimeMillis());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: ");
        super.onDetach();
    }
}