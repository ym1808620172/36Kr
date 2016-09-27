package com.sunhongxu.a36kr.controler.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/9/8.
 *  获得应用的Context
 * @author sunhongxu
 */
public class KrApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    //提供get方法
    public static Context getContext() {
        return context;
    }
}
