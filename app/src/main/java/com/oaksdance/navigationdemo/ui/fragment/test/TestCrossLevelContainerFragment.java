package com.oaksdance.navigationdemo.ui.fragment.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseFragment;
import com.oaksdance.navigationdemo.navigation.KeepStateNavigator;

import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class TestCrossLevelContainerFragment extends BaseFragment {
    private static final String TAG = "TestCrossLevelContainerFragment";
    private int checkedId;
    private NavController navController;

    public static TestCrossLevelContainerFragment getInstance() {
        return new TestCrossLevelContainerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_test_cross_level_container, container, false);

        // get fragment
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.test_cross_level_host_fragment);
        // setup custom navigator
        Navigator navigator = new KeepStateNavigator(inflater.getContext(), navHostFragment.getChildFragmentManager(), R.id.test_cross_level_host_fragment);
        navController = navHostFragment.getNavController();
        navController.getNavigatorProvider().addNavigator(navigator);
        // set navigation graph
        navController.setGraph(R.navigation.test_cross_level_container_navigation);

        RadioGroup rg_test = mContentView.findViewById(R.id.rg_test);
        if (this.checkedId == 0) {
            this.checkedId = R.id.rb_test1;
        }
        rg_test.check(this.checkedId);
        rg_test.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TestCrossLevelContainerFragment.this.checkedId = checkedId;
                select();
            }
        });
        return mContentView;
    }

    private void select() {
        Log.d(TAG, "select: "+this.checkedId);
        navController.navigate(getResId());
    }

    private int getResId() {
        switch (this.checkedId) {
            case R.id.rb_test1:
                return R.id.test1ContainerFragment;
            case R.id.rb_test2:
                return R.id.test2Fragment;
            default:
                return R.id.test3Fragment;
        }
    }

}