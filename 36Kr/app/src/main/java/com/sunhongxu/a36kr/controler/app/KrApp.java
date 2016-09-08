package com.sunhongxu.a36kr.controler.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/9/8.
 *
 * @author sunhongxu
 */
public class KrApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
