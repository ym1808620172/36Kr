package com.sunhongxu.a36kr.controler.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/8.
 * 我的界面
 */
public class MineFragment extends AbsBaseFragment {
    public static MineFragment newInstance() {
        
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private LinearLayout linearLayout;

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        linearLayout = byView(R.id.mine_title);
    }

    @Override
    protected void initDatas() {
        linearLayout.setPadding(50, MarginTop()+50, 50, 50);
    }
}
