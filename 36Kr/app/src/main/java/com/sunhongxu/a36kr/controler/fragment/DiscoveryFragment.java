package com.sunhongxu.a36kr.controler.fragment;

import android.os.Bundle;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/8.
 */
public class DiscoveryFragment extends AbsBaseFragment {
    public static DiscoveryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_discovery;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
