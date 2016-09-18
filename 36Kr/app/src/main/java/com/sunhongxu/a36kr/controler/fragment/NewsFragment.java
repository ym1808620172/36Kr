package com.sunhongxu.a36kr.controler.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.MainActivity;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.fragment.news.NewsAllFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsVpFragment;
import com.sunhongxu.a36kr.model.net.VolleyInstance;

/**
 * Created by dllo on 16/9/8.
 * 新闻界面的Fragment
 *
 * @author sunhongxu
 */
public class NewsFragment extends AbsBaseFragment implements View.OnClickListener {
    private FrameLayout frameLayout;
    private ImageView titleNavigation, titlesActivity;
    private LinearLayout titles;
    private TextView titleTv;
    private ImageView searchImg;
    private Intent intent;
    private NewsVpFragment vpFragment;
    private Runnable runnable;
    private ViewPager viewPager;
    private FragmentManager fragmentManager;

    public static NewsFragment newInstance() {

        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    //初始化控件
    @Override
    protected void initView() {
        frameLayout = byView(R.id.framelayout_news);
        titleNavigation = byView(R.id.title_img_navigation);
        titlesActivity = byView(R.id.title_activity);
        titles = byView(R.id.root_title);
        titlesActivity.setVisibility(View.GONE);
        titleTv = byView(R.id.title_tv);
        searchImg = byView(R.id.title_search);
        searchImg.setOnClickListener(this);
        titleNavigation.setOnClickListener(this);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //设置默认页
        setFragment();

        //设置Fragment外边距为电量栏高度
        titles.setPadding(0, MarginTop(), 0, 0);
        //请求数据


    }

    private Handler handler;

    private void setFragment() {
        fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout_news, NewsVpFragment.newInstance(), "tag");
        transaction.commit();
        if (fragmentManager.executePendingTransactions()) {
            vpFragment = (NewsVpFragment) fragmentManager.findFragmentByTag("tag");
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    viewPager = vpFragment.getViewPager();
                    if (viewPager == null) {
                        handler.postDelayed(runnable, 100);
                    }
                    Log.d("xxx", "viewPager:" + viewPager);
                }
            };
            handler.postDelayed(runnable, 100);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发广播
            case R.id.title_img_navigation:
                intent = new Intent();
                intent.setAction("com.sunhongxu.a36kr.controler.fragment.NewsFragment");
                context.sendBroadcast(intent);
                break;
            case R.id.title_search:
                intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
                break;
        }
    }


}
