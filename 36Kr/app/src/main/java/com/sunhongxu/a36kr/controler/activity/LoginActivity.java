package com.sunhongxu.a36kr.controler.activity;


import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.LoginAdapter;
import com.sunhongxu.a36kr.controler.fragment.login.Login_Fragment;
import com.sunhongxu.a36kr.controler.fragment.login.Register_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录界面
 */

public class LoginActivity extends AbsBaseActivity {


    private RelativeLayout rootLl;//根布局
    private ViewPager loginVp;//定义ViewPager
    private List<Fragment> fragments;//定义数组
    private TabLayout loginTab;//定义登录界面的TabLayout

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    //初始化组件
    @Override
    protected void initView() {
        rootLl = byView(R.id.login_root);
        loginVp = byView(R.id.login_vp);
        loginTab = byView(R.id.login_tab);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //初始化数组
        fragments = new ArrayList<>();
        //添加数据
        fragments.add(Login_Fragment.newInstance());
        fragments.add(Register_Fragment.newInstance());
        //初始化适配器并绑定
        LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), fragments);
        loginVp.setAdapter(adapter);
        //设置下滑条颜色
        loginTab.setSelectedTabIndicatorColor(Color.WHITE);
        //tanLayout与ViewPager联动
        loginTab.setupWithViewPager(loginVp);
        rootLl.setPadding(20,MarginTop()+20,20,20);
    }
}
