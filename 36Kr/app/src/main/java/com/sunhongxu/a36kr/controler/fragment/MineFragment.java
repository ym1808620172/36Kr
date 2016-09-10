package com.sunhongxu.a36kr.controler.fragment;

import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/8.
 */
public class MineFragment extends AbsBaseFragment {

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
