package com.opensource.giantturtle.clientapp.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/*
Used by LeakCanary for memory leak detection (only in debug version of the app).
*/

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
