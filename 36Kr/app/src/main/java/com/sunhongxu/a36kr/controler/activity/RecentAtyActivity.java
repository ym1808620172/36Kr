package com.sunhongxu.a36kr.controler.activity;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.RecentAtyAdapter;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.view.RefreshLayout;

import java.util.List;

/**
 * 近期活动界面
 */

public class RecentAtyActivity extends AbsBaseActivity implements View.OnClickListener, VolleyRequest, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回按钮
    private ListView recentList;//定义ListView
    private RecentAtyAdapter atyAdapter;//定义适配器
    private com.sunhongxu.a36kr.view.RefreshLayout refreshLayout;//定义RefreshLayout下拉刷新
    private TextView titleTv;//设置标题


    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_recent_aty;
    }

    //初始化组件
    @Override
    protected void initView() {
        backImg = byView(R.id.find_people_back_img);
        TextView imageViewTitle = byView(R.id.visibity);
        //将占位的隐藏
        imageViewTitle.setVisibility(View.INVISIBLE);
        rootTitle = byView(R.id.recent_aty_root);
        backImg.setOnClickListener(this);
        recentList = byView(R.id.recent_list_view);
        refreshLayout = byView(R.id.recent_swipe);
        titleTv = byView(R.id.title_find_tv);
    }

    @Override
    protected void initDatas() {
        titleTv.setText("近期活动");
        //设置高度
        rootTitle.setPadding(0, MarginTop(), 0, 0);
        //初始化适配器并绑定
        atyAdapter = new RecentAtyAdapter(this);
        recentList.setAdapter(atyAdapter);
        //网络请求数据
        VolleyInstance.getInstance().startInstance(NetConstants.RECENTATY, this);
        //设置下拉刷新的小圈圈的颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //设置下拉刷新监听
        refreshLayout.setOnRefreshListener(this);
        //设置ListView的行布局点击监听
        recentList.setOnItemClickListener(this);
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
    //下拉刷新
    @Override
    public void onRefresh() {
        //开线程,重新请求一次网络数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                VolleyInstance.getInstance().startInstance(NetConstants.RECENTATY, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        RecentAtyBean atyBean = gson.fromJson(result, RecentAtyBean.class);
                        List<RecentAtyBean.DataBean.DataBeans> dataBeanses = atyBean.getData().getData();
                        atyAdapter.setDataBeanses(dataBeanses);
                        //成功后将小圈圈隐藏
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure() {

                    }
                });
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击跳转详细页,把网址传过去
        RecentAtyBean.DataBean.DataBeans dataBeans = (RecentAtyBean.DataBean.DataBeans) parent.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putString("URL", dataBeans.getActivityLink());
        goTo(RecentAtyActivity.this, RotateDerailsActivity.class, bundle);
    }
}
