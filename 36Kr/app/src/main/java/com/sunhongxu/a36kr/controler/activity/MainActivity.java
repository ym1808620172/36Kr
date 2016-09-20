package com.sunhongxu.a36kr.controler.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.MainAdapter;
import com.sunhongxu.a36kr.controler.fragment.DiscoveryFragment;
import com.sunhongxu.a36kr.controler.fragment.EquityFragment;
import com.sunhongxu.a36kr.controler.fragment.MessageFragment;
import com.sunhongxu.a36kr.controler.fragment.MineFragment;
import com.sunhongxu.a36kr.controler.fragment.NewsFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsAllFragment;
import com.sunhongxu.a36kr.utils.IOpenDrawer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 * 主界面
 *
 * @author sunhongxu
 */
public class MainActivity extends AbsBaseActivity implements View.OnClickListener, IOpenDrawer {
    private TabLayout mainTl;//定义TabLayout
    private ViewPager mainVp;//定义主界面的ViewPager
    private MainAdapter mainAdapter;//定义主界面的适配器
    private List<Fragment> datas;
    private DrawerLayout drawerLayout;//定义抽屉
    private LinearLayout linearLayout;//抽屉界面的根布局
    private ImageView imageViewReturn;//抽屉的返回按钮图片
    private LinearLayout drawerAll;//抽屉全部
    private LinearLayout drawerEarlyPhase;//抽屉早期
    private LinearLayout drawerBEnd;//抽屉B轮后
    private LinearLayout drawerBigCompany;//抽屉大公司
    private LinearLayout drawerCapital;//抽屉资本
    private LinearLayout drawerDepth;//抽屉深度
    private LinearLayout drawerStudy;//抽屉研究
    private NewsFragment fragment;//定义NewsFragment

    //加载布局
    @Override
    protected int setLayout() {
        //初始化Fragment
        fragment = NewsFragment.newInstance();
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
        datas = new ArrayList<>();
        datas.add(fragment);
        datas.add(EquityFragment.newInstance());
        datas.add(DiscoveryFragment.newInstance());
        datas.add(MessageFragment.newInstance());
        datas.add(MineFragment.newInstance());


        //初始化Adapter
        mainAdapter = new MainAdapter(getSupportFragmentManager(), datas);
        mainVp.setAdapter(mainAdapter);
        //设置TabLayout与ViewPager联动
        mainTl.setupWithViewPager(mainVp);
        //设置TabLayout滑动监听
        setTabLayout();
        //设置监听
        listener();
        //设置抽屉的内边距，MarginTop：电量栏的高度
        linearLayout.setPadding(0, MarginTop(), 0, 0);
    }

    //设置监听
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

    private void setTabLayout() {
        //设置当前页
        for (int i = 0; i < mainTl.getTabCount(); i++) {
            mainTl.getTabAt(i).setCustomView(mainAdapter.getView(i));
            //当当前页为0时，设置当前页为true
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
//               将离开的tab的textView的select属性设置为true
                tab.getCustomView().findViewById(R.id.item_img_tablayout).setSelected(true);
                tab.getCustomView().findViewById(R.id.item_tv_tablayout).setSelected(true);
                // 将viewpager的item与 tablayout的同步
                mainVp.setCurrentItem(tab.getPosition());
                Log.d("MainActivity", "tab.getPosition():" + tab.getPosition());

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
        switch (v.getId()) {
            case R.id.drawer_img_back:
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_all:
                //用回调的方法加载新闻界面对应布局
                fragment.changeFragment(NewsAllFragment.newInstance("all"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_early_phase:
                fragment.changeFragment(NewsAllFragment.newInstance("67"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_B_end:
                fragment.changeFragment(NewsAllFragment.newInstance("68"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_big_company:
                fragment.changeFragment(NewsAllFragment.newInstance("23"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_capital:
                fragment.changeFragment(NewsAllFragment.newInstance("69"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_depth:
                fragment.changeFragment(NewsAllFragment.newInstance("70"));
                drawerLayout.closeDrawer(linearLayout);
                break;
            case R.id.drawer_study:
                fragment.changeFragment(NewsAllFragment.newInstance("71"));
                drawerLayout.closeDrawer(linearLayout);
                break;
        }
    }

    @Override
    public void onIOpenDrawer(int index) {
        if (index == 0) {
            drawerLayout.openDrawer(linearLayout);
        }
        if (index == 1) {
            mainVp.setCurrentItem(1);
        }
    }


    private long exitTime = 0;


    //物理返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
