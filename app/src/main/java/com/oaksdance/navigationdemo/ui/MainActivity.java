package com.oaksdance.navigationdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseActivity;
import com.oaksdance.navigationdemo.navigation.KeepStateNavigator;
import com.jidouauto.ui.oushang.sideBar.JSideBar;

import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private int position;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((JSideBar) findViewById(R.id.sidebar)).setMenuItemSelectListener((position, item) -> showFragment(position)).showMenus();

        // get fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);

        // setup custom navigator
        Navigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.main_nav_host_fragment);
        navHostFragment.getNavController().getNavigatorProvider().addNavigator(navigator);

        // set navigation graph
        navHostFragment.getNavController().setGraph(R.navigation.main_navigation);

        if (savedInstanceState == null) {
            position = 0;
        }
        if (position > 0) {
            showFragment(position);
        }
    }

    private void showFragment(int position) {
        Navigation.findNavController(this, R.id.main_nav_host_fragment).navigate(getResId(position));
    }

    private int getResId(int position) {
        switch (position) {
            case 1:
                return R.id.blueToolFragment;
            case 2:
                return R.id.screenFragment;
            case 3:
                return R.id.soundFragment;
            case 4:
                return R.id.commonFragment;
            default:
                return R.id.netContainerFragment;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Log.d(TAG, "onSupportNavigateUp: ");
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
