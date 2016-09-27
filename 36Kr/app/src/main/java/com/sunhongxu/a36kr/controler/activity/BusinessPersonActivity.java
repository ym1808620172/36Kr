package com.sunhongxu.a36kr.controler.activity;


import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;

/**
 * 我是创业者界面
 * @author sunhongxu
 */

public class BusinessPersonActivity extends AbsBaseActivity {

    private LinearLayout rootLl;//根布局
    private TextView commitTv,titleTv;//头布局的标题和提交公司TextView

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_business_person;
    }

    //初始化组件
    @Override
    protected void initView() {
        rootLl = byView(R.id.business_root_ll);
        commitTv = byView(R.id.visibity);
        titleTv = byView(R.id.title_find_tv);
    }

    //设置数据
    @Override
    protected void initDatas() {
        rootLl.setPadding(0, MarginTop(), 0, 0);
        commitTv.setText("提交公司");
        titleTv.setText("创业者");
    }
}
