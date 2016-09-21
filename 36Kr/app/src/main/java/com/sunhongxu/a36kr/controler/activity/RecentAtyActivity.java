package com.sunhongxu.a36kr.controler.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.RecentAtyAdapter;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * 近期活动界面
 */

public class RecentAtyActivity extends AbsBaseActivity implements View.OnClickListener, VolleyRequest {
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回按钮
    private ListView recentList;//定义ListView
    private RecentAtyAdapter atyAdapter;//定义适配器

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_recent_aty;
    }

    //初始化组件
    @Override
    protected void initView() {
        backImg = byView(R.id.find_people_back_img);
        ImageView imageViewTitle = byView(R.id.visibity);
        imageViewTitle.setVisibility(View.INVISIBLE);
        rootTitle = byView(R.id.recent_aty_root);
        backImg.setOnClickListener(this);
        recentList = byView(R.id.recent_list_view);
    }

    @Override
    protected void initDatas() {
        //设置高度
        rootTitle.setPadding(0, MarginTop(), 0, 0);
        //初始化适配器并绑定
        atyAdapter = new RecentAtyAdapter(this);
        recentList.setAdapter(atyAdapter);
        //网络请求数据
        VolleyInstance.getInstance().startInstance(NetConstants.RECENTATY, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_people_back_img:
                //结束本界面
                finish();
                break;
        }
    }

    @Override
    public void success(String result) {
        //数据解析并传入适配器
        Gson gson = new Gson();
        RecentAtyBean atyBean = gson.fromJson(result, RecentAtyBean.class);
        List<RecentAtyBean.DataBean.DataBeans> dataBeanses = atyBean.getData().getData();
        atyAdapter.setDataBeanses(dataBeanses);
    }

    @Override
    public void failure() {

    }
}
