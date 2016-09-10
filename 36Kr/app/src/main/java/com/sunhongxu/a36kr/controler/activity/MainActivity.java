package com.sunhongxu.a36kr.controler.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.MainAdapter;
import com.sunhongxu.a36kr.controler.fragment.DiscoveryFragment;
import com.sunhongxu.a36kr.controler.fragment.EquityFragment;
import com.sunhongxu.a36kr.controler.fragment.MessageFragment;
import com.sunhongxu.a36kr.controler.fragment.MineFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsAllFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsBEndFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsBigFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsCapitalFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsDepthFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsEarlyFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsStudyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 * 主界面
 *
 * @author sunhongxu
 */
public class MainActivity extends AbsBaseActivity implements View.OnClickListener {
    private TabLayout mainTl;
    private ViewPager mainVp;
    private MainAdapter mainAdapter;
    private List<Fragment> fragments;
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayout;
    private MyBroad myBroad;
    private ImageView imageViewReturn;
    private LinearLayout drawerAll;
    private LinearLayout drawerEarlyPhase;
    private LinearLayout drawerBEnd;
    private LinearLayout drawerBigCompany;
    private LinearLayout drawerCapital;
    private LinearLayout drawerDepth;
    private LinearLayout drawerStudy;

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
        drawerLayout = byView(R.id.root_drawer_layout);
        linearLayout = byView(R.id.drawer_view);
        imageViewReturn = byView(R.id.drawer_img_back);
        drawerAll = byView(R.id.drawer_all);
        drawerEarlyPhase = byView(R.id.drawer_early_phase);
        drawerBEnd = byView(R.id.drawer_B_end);
        drawerBigCompany = byView(R.id.drawer_big_company);
        drawerCapital = byView(R.id.drawer_capital);
        drawerDepth = byView(R.id.drawer_depth);
        drawerStudy = byView(R.id.drawer_study);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //添加Fragment
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new EquityFragment());
        fragments.add(new DiscoveryFragment());
        fragments.add(new MessageFragment());
        fragments.add(new MineFragment());

        //初始化Adapter
        mainAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mainVp.setAdapter(mainAdapter);
        //设置TabLayout与ViewPager联动
        mainTl.setupWithViewPager(mainVp);
        //设置TabLayout滑动监听
        setTabLayout();
        //注册广播
        BroadIntent();
        //设置监听
        listener();

    }

    private void listener() {
        imageViewReturn.setOnClickListener(this);
        drawerAll.setOnClickListener(this);
        drawerEarlyPhase.setOnClickListener(this);
        drawerBEnd.setOnClickListener(this);
        drawerBigCompany.setOnClickListener(this);
        drawerCapital.setOnClickListener(this);
        drawerDepth.setOnClickListener(this);
        drawerStudy.setOnClickListener(this);
    }

    private void BroadIntent() {
        myBroad = new MyBroad();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sunhongxu.a36kr.controler.fragment.NewsFragment");
        registerReceiver(myBroad, intentFilter);
        linearLayout.setPadding(0, MarginTop(), 0, 0);
    }

    private void setTabLayout() {
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
                //如果当前位置为0时,设置抽屉为不锁定的,否则为锁定
                if (tab.getPosition() == 0) {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                } else {
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
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

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.drawer_img_back:
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_all:
                transaction.replace(R.id.framelayout_news, new NewsAllFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_early_phase:
                transaction.replace(R.id.framelayout_news, new NewsEarlyFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_B_end:
                transaction.replace(R.id.framelayout_news, new NewsBEndFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_big_company:
                transaction.replace(R.id.framelayout_news, new NewsBigFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_capital:
                transaction.replace(R.id.framelayout_news, new NewsCapitalFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_depth:
                transaction.replace(R.id.framelayout_news, new NewsDepthFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_study:
                transaction.replace(R.id.framelayout_news, new NewsStudyFragment());
                drawerLayout.closeDrawer(linearLayout);
                break;
        }
        transaction.commit();
    }

    //广播接收者
    private class MyBroad extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            drawerLayout.openDrawer(linearLayout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroad);
    }
}
