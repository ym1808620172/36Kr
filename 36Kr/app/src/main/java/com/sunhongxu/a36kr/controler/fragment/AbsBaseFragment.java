package com.sunhongxu.a36kr.controler.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.sunhongxu.a36kr.controler.activity.AbsBaseActivity;
import com.sunhongxu.a36kr.controler.app.KrApp;

/**
 * Created by dllo on 16/9/8.
 * fragment基类
 *
 * @author sunhongxu
 */
public abstract class AbsBaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化控件
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //加载数据
        initDatas();
    }

    /**
     * 绑定布局
     *
     * @return R.layout.xx
     */
    protected abstract int setLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void initDatas();

    /**
     * 简化findViewById
     *
     * @param resId 控件Id
     * @param <T>   控件
     */
    protected <T extends View> T byView(int resId) {
        return (T) getView().findViewById(resId);
    }

    /**
     * 获得电量栏的高度
     *
     * @return 电量栏的高度
     */
    protected static int MarginTop() {
        int statusHeight = 0;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = KrApp.getContext().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;

    }

    /**
     * 不带返回值跳转
     *
     * @param to 目标界面
     */
    protected void goTo(Class<? extends AbsBaseActivity> to) {
        context.startActivity(new Intent(context, to));
    }

    protected void goTo(Class<? extends AbsBaseActivity> to, Bundle extras) {
        Intent intent = new Intent(context, to);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


}
