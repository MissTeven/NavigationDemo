package com.oaksdance.navigationdemo.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NavigationUtil {
    private static final String TAG = "JNavigationUtil";
    public static void takeAction(Fragment f, int fragmentId, int actionId) {
        Observable.create(new ObservableOnSubscribe<NavController>() {
            @Override
            public void subscribe(ObservableEmitter<NavController> e) throws Exception {
                Fragment fragment = f;
                NavController navController = null;
                do {
                    FragmentManager fragmentManager = fragment.getFragmentManager();
                    if (fragmentManager != null) {
                        Fragment tf = fragmentManager.findFragmentById(fragmentId);
                        if (tf instanceof NavHostFragment) {
                            navController = ((NavHostFragment) tf).getNavController();
                        }
                    }
                    fragment = fragment.getParentFragment();
                } while (navController == null && fragment != null);
                if (navController != null) {
                    e.onNext(navController);
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<NavController>() {
            @Override
            public void accept(NavController navController) throws Exception {
                navController.navigate(actionId);
            }
        });
    }
}
