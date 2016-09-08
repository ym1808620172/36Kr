package com.sunhongxu.a36kr.controler.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/8.
 * Activity基类
 *
 * @author sunhongxu
 */
public abstract class AbsBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //制定流程
        setContentView(setLayout());
        initView();
        initDatas();
    }

    /**
     * 加载布局
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
     * findViewById
     *
     * @param resId 控件ID
     * @param <T>   控件
     */
    protected <T extends View> T byView(int resId) {
        return (T) findViewById(resId);
    }

    /**
     * 不带返回值跳转
     *
     * @param from 当前界面
     * @param to   目标界面
     */
    protected void goTo(Context from, Class<? extends AbsBaseActivity> to) {
        startActivity(new Intent(from, to));
    }

    protected void goTo(Context from, Class<? extends AbsBaseActivity> to, Bundle extras) {
        Intent intent = new Intent(from, to);
        intent.putExtras(extras);
        startActivity(intent);

    }

    /**
     * 加动画
     */
    @Override
    public void finish() {
        super.finish();
    }
}
