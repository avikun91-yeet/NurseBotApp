package com.ra.nursebot.app;

import android.app.Application;
import android.content.Context;

import androidx.viewbinding.BuildConfig;

import com.ra.nursebot.utils.TagTree;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class App extends Application {
    private static WeakReference<App> mInstance;
    private ExecutorService executorService;
    
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = new WeakReference<>(this);
        executorService = Executors.newFixedThreadPool(1);
        if (BuildConfig.DEBUG) {
            Timber.plant(new TagTree("NursingBotApp", true));
        }
    }

    public static Context getAppContext() {
        return mInstance.get();
    }

    public static App getInstance() {
        return mInstance.get();
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
