package com.oaksdance.navigationdemo;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Application sApplication;

    @Override
    public void onCreate() {
        sApplication = this;
        super.onCreate();
    }

    public static Context getContent() {
        return sApplication.getApplicationContext();
    }
}
