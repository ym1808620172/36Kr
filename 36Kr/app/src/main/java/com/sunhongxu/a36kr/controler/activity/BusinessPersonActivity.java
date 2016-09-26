package com.sunhongxu.a36kr.controler.activity;


import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;

public class BusinessPersonActivity extends AbsBaseActivity {

    private LinearLayout rootLl;
    private TextView commitTv,titleTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_business_person;
    }

    @Override
    protected void initView() {
        rootLl = byView(R.id.business_root_ll);
        commitTv = byView(R.id.visibity);
        titleTv = byView(R.id.title_find_tv);
    }

    @Override
    protected void initDatas() {
        rootLl.setPadding(0, MarginTop(), 0, 0);
        commitTv.setText("提交公司");
        titleTv.setText("创业者");
    }
}
