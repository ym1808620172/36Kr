package com.sunhongxu.a36kr.controler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 * 股权投资界面,用于ViewPager滑动改变界面
 */
public class EquityFragmentAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[]{"全部", "募资中", "募资完成", "融资完成"};
    private List<Fragment> fragments;

    public EquityFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
