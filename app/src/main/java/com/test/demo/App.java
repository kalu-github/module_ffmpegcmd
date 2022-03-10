package com.test.demo;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

/**
 * Application
 * Created by Super on 2018/12/7.
 */

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}