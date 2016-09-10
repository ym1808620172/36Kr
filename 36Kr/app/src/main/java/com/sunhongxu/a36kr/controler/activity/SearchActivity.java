package com.sunhongxu.a36kr.controler.activity;


import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;

public class SearchActivity extends AbsBaseActivity {


    private LinearLayout linearLayout;

    @Override
    protected int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        linearLayout = byView(R.id.search_root_ll);
    }

    @Override
    protected void initDatas() {
        linearLayout.setPadding(0, MarginTop(), 0, 0);
    }
}
