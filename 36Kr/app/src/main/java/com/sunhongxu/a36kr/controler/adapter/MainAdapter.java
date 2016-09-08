package com.sunhongxu.a36kr.controler.adapter;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.app.KrApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 * 主界面的适配器
 */
public class MainAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private int[] imageViewtitles = new int[]{R.drawable.tabber_news, R.drawable.tabber_equity, R.drawable.tabber_discovery, R.drawable.tabber_mine};
    private List<String> titles = new ArrayList<>();

    public MainAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
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
        return titles.get(position);
    }

    public View getView(int position) {
        View view = LayoutInflater.from(KrApp.getContext()).inflate(R.layout.item_tablayout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_img_tablayout);
        TextView textView = (TextView) view.findViewById(R.id.item_tv_tablayout);
        imageView.setImageResource(imageViewtitles[position]);
        textView.setText(titles.get(position));
        return view;

    }

}
