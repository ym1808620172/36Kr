package com.sunhongxu.a36kr.controler.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.MainAdapter;
import com.sunhongxu.a36kr.controler.fragment.DiscoveryFragment;
import com.sunhongxu.a36kr.controler.fragment.EquityFragment;
import com.sunhongxu.a36kr.controler.fragment.MineFragment;
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
    private List<String> titles = new ArrayList<>();

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
        fragments.add(new EquityFragment());
        fragments.add(new DiscoveryFragment());
        fragments.add(new MineFragment());



        mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mainVp.setAdapter(mainAdapter);

        mainTl.setupWithViewPager(mainVp);
        for (int i = 0; i < mainTl.getTabCount(); i++) {
            mainTl.getTabAt(i).setCustomView(mainAdapter.getView(i));
            if (i == 0) {
                mainTl.getTabAt(i).getCustomView().findViewById(R.id.item_tv_tablayout).setSelected(true);
                mainTl.getTabAt(i).getCustomView().findViewById(R.id.item_img_tablayout).setSelected(true);
            }
        }
        mainTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 将离开的tab的textView的select属性设置为true
                tab.getCustomView().findViewById(R.id.item_img_tablayout).setSelected(true);
                tab.getCustomView().findViewById(R.id.item_tv_tablayout).setSelected(true);
                // 将viewpager的item与 tablayout的同步
                mainVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 将离开的tab的textView的select属性设置为false
                tab.getCustomView().findViewById(R.id.item_img_tablayout).setSelected(false);
                tab.getCustomView().findViewById(R.id.item_tv_tablayout).setSelected(false);
                // 将viewpager的item与 tablayout的同步
                mainVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
