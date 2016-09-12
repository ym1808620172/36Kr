package com.sunhongxu.a36kr.controler.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.AbsBaseActivity;
import com.sunhongxu.a36kr.controler.activity.MainActivity;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.fragment.news.NewsAllFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsBEndFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsBigFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsCapitalFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsDepthFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsEarlyFragment;
import com.sunhongxu.a36kr.controler.fragment.news.NewsStudyFragment;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NewsNetHelper;

import java.lang.reflect.Field;

/**
 * Created by dllo on 16/9/8.
 * 新闻界面的Fragment
 *
 * @author sunhongxu
 */
public class NewsFragment extends AbsBaseFragment implements View.OnClickListener, VolleyRequest, MainActivity.ToChangeFragment {
    private FrameLayout frameLayout;
    private ImageView titleNavigation, titlesActivity;
    private LinearLayout titles;
    private TextView titleTv;
    private ImageView searchImg;
    private Intent intent;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
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
        VolleyInstance.getInstance().startInstance(NewsNetHelper.NEWSHELER, this);

    }


    private void setFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments() == null) {
            transaction.replace(R.id.framelayout_news, new NewsAllFragment());
            transaction.commit();
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

    @Override
    public void success(String result) {
        Log.d("NewsFragment", result + "");
    }

    @Override
    public void failure() {
        Log.d("NewsFragment", "请求失败");
    }

    @Override
    public void onToChangeFragment(int index) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        switch (index) {
            case 0:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsAllFragment());
                break;
            case 1:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsEarlyFragment());
                break;
            case 2:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsBEndFragment());
                break;
            case 3:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsBigFragment());
                break;
            case 4:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsCapitalFragment());
                break;
            case 5:
                Log.d("NewsFragment", "index:" + index);
                ft.add(R.id.framelayout_news, new NewsStudyFragment());
                break;
            case 6:
                ft.add(R.id.framelayout_news, new NewsDepthFragment());
        }
        Log.d("NewsFragment", "manager.getFragments():" + ft);
//        ft.commit();
    }
}
