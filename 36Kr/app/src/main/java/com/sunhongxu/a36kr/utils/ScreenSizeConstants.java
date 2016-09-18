package com.sunhongxu.a36kr.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.sunhongxu.a36kr.controler.app.KrApp;

/**
 * Created by dllo on 16/9/13.
 * 获取屏幕尺寸帮助类
 */
public final class ScreenSizeConstants {
    public enum ScreenState {
        WIDTH, HEIGHT;
    }

    public static int getScreenSize(Context context, ScreenState state) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        if (ScreenState.WIDTH == state) {
            return metrics.widthPixels;
        } else {
            return metrics.heightPixels;
        }
    }
}
