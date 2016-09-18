package com.sunhongxu.a36kr.controler.fragment.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.MainActivity;
import com.sunhongxu.a36kr.controler.adapter.NewsVpAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/18.
 * 新闻界面的ViewPager,用于接口回调
 */
public class NewsVpFragment extends AbsBaseFragment implements MainActivity.ToChangeFragment {

    private ViewPager viewPager;
    private NewsVpAdapter newsVpAdapter;

    public static NewsVpFragment newInstance() {
        NewsVpFragment fragment = new NewsVpFragment();
        return fragment;
    }

    public ViewPager getViewPager() {
        ViewPager viewPager1 = viewPager;
        return viewPager;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_vp_news;
    }

    @Override
    protected void initView() {
        viewPager = byView(R.id.fragment_news_vp);
    }

    @Override
    protected void initDatas() {
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(NewsAllFragment.newInstance("all"));
        fragments.add(NewsAllFragment.newInstance("69"));
        fragments.add(NewsAllFragment.newInstance("70"));
        fragments.add(NewsAllFragment.newInstance("71"));
        fragments.add(NewsAllFragment.newInstance("all"));
        fragments.add(NewsAllFragment.newInstance("all"));
        fragments.add(NewsAllFragment.newInstance("all"));

        newsVpAdapter = new NewsVpAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(newsVpAdapter);
        Bundle bundle = getArguments();
        Log.d("xxx", "bundle:" + bundle);
    }

    @Override
    public void onToChangeFragment(int index) {
        Bundle b = getArguments();
        Log.d("xxxx", "b:" + b);
    }
}
