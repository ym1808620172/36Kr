package com.sunhongxu.a36kr.utils;

import com.sunhongxu.a36kr.view.ObservableScrollView;

/**
 * Created by dllo on 16/9/14.
 * ScrollView监听事件的接口
 */
public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
