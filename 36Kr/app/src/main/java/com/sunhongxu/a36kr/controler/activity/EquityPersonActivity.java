package com.sunhongxu.a36kr.controler.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;

/**
 * 我是专业投资人界面
 */

public class EquityPersonActivity extends AbsBaseActivity {
    private TextView commitTv, titleTv;//定义标题内容
    private LinearLayout myRootLl;//定义根布局

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_my_equity_person;
    }

    //初始化组件
    @Override
    protected void initView() {
        commitTv = byView(R.id.visibity);
        titleTv = byView(R.id.title_find_tv);
        myRootLl = byView(R.id.my_eq_person);
    }

    //加载数据
    @Override
    protected void initDatas() {
        commitTv.setText("立即认证");
        titleTv.setText("创业者");
        myRootLl.setPadding(0, MarginTop(), 0, 0);
    }
}
