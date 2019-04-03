package com.oaksdance.navigationdemo.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.jidouauto.setting.R;

import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
@Navigator.Name("fragment")
public class JFragmentNavigator extends FragmentNavigator {
    private static final String TAG = "JFragmentNavigator";
    public JFragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        super(context, manager, containerId);
    }

    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        Log.d(TAG, "navigate: "+System.currentTimeMillis());
        if (navOptions == null) {
            navOptions = new NavOptions.Builder().setEnterAnim(R.anim.slide).build();
        }
        return super.navigate(destination, args, navOptions, navigatorExtras);
    }
}
