package com.oaksdance.navigationdemo.navigation;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.jidouauto.setting.R;

import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;

public class JNavigationUtil {
    private static final String TAG = "JNavigationUtil";
    public static void inject(Fragment fragment, int hostResId, int graphResId) {
        try {
            NavHostFragment navHostFragment = (NavHostFragment) fragment.getChildFragmentManager().findFragmentById(hostResId);
            // setup custom navigator
            Navigator navigator = new JFragmentNavigator(fragment.getContext(), navHostFragment.getChildFragmentManager(), hostResId);
            NavController navController = navHostFragment.getNavController();
            navController.getNavigatorProvider().addNavigator(navigator);
            // set navigation graph
            navController.setGraph(graphResId);
        } catch (Exception ex) {
            Log.e(TAG, "inject: " + ex.getMessage());
        }

    }
}
