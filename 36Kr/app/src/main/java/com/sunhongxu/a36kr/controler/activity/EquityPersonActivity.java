package com.sunhongxu.a36kr.controler.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;

public class EquityPersonActivity extends AbsBaseActivity {
    private TextView commitTv, titleTv;
    private LinearLayout myRootLl;

    @Override
    protected int setLayout() {
        return R.layout.activity_my_equity_person;
    }

    @Override
    protected void initView() {
        commitTv = byView(R.id.visibity);
        titleTv = byView(R.id.title_find_tv);
        myRootLl = byView(R.id.my_eq_person);
    }

    @Override
    protected void initDatas() {
        commitTv.setText("立即认证");
        titleTv.setText("创业者");
        myRootLl.setPadding(0, MarginTop(), 0, 0);
    }
}
