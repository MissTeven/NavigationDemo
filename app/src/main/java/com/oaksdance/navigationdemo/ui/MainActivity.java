package com.oaksdance.navigationdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.jidouauto.setting.R;
import com.oaksdance.navigationdemo.base.BaseActivity;
import com.oaksdance.navigationdemo.navigation.KeepStateNavigator;
import com.jidouauto.ui.oushang.sideBar.JSideBar;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private int mPosition;
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((JSideBar) findViewById(R.id.sidebar)).setMenuItemSelectListener((position, item) -> {
            showFragment(position, mPosition);
        }).showMenus();
        // get fragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);
        // setup custom navigator
        Navigator navigator = new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.main_nav_host_fragment);
        navHostFragment.getNavController().getNavigatorProvider().addNavigator(navigator);
        // set navigation graph
        navHostFragment.getNavController().setGraph(R.navigation.main_navigation);
    }

    private void showFragment(int position, int lastPosition) {
        NavOptions options = null;
        if (position > lastPosition) {
            options = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_top_in)
                    .setExitAnim(R.anim.slide_bottom_out)
                    .build();
        } else if (position < lastPosition) {
            options = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_bottom_in)
                    .setExitAnim(R.anim.slide_top_out)
                    .build();
        }
        mPosition = position;
        Navigation.findNavController(this, R.id.main_nav_host_fragment).navigate(getResId(position), null, options);
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
                return R.id.testCrossLevelFragment;
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
