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

public class LoginActivity extends AbsBaseActivity {


    private RelativeLayout rootLl;
    private ViewPager loginVp;
    private List<Fragment> fragments;
    private TabLayout loginTab;

    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        rootLl = byView(R.id.login_root);
        loginVp = byView(R.id.login_vp);
        loginTab = byView(R.id.login_tab);
    }

    @Override
    protected void initDatas() {
        fragments = new ArrayList<>();
        fragments.add(Login_Fragment.newInstance());
        fragments.add(Register_Fragment.newInstance());
        LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), fragments);
        loginVp.setAdapter(adapter);
        loginTab.setSelectedTabIndicatorColor(Color.WHITE);
        loginTab.setupWithViewPager(loginVp);
        rootLl.setPadding(20,MarginTop()+20,20,20);
    }
}
