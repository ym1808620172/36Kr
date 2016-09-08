package com.sunhongxu.a36kr.controler.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.MainAdapter;
import com.sunhongxu.a36kr.controler.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 * 主界面
 *
 * @author sunhongxu
 */
public class MainActivity extends AbsBaseActivity {
    private TabLayout mainTl;
    private ViewPager mainVp;
    private MainAdapter mainAdapter;
    private List<Fragment> fragments;

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    //绑定控件
    @Override
    protected void initView() {
        mainVp = byView(R.id.main_vp);
        mainTl = byView(R.id.main_tl);
    }

    //加载数据
    @Override
    protected void initDatas() {
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new NewsFragment());

        mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mainVp.setAdapter(mainAdapter);

        mainTl.setupWithViewPager(mainVp);
    }
}
