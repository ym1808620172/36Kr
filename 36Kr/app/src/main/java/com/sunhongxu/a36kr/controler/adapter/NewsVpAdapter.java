package com.sunhongxu.a36kr.controler.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dllo on 16/9/18.
 */
public class NewsVpAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public NewsVpAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments != null && fragments.size() > 0 ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments != null && fragments.size() > 0 ? fragments.size() : 0;
    }
}
