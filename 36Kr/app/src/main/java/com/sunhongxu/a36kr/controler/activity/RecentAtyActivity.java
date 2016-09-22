package com.sunhongxu.a36kr.controler.activity;


import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.RecentAtyAdapter;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.view.RefreshLayout;

import java.util.List;

/**
 * 近期活动界面
 */

public class RecentAtyActivity extends AbsBaseActivity implements View.OnClickListener, VolleyRequest, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回按钮
    private ListView recentList;//定义ListView
    private RecentAtyAdapter atyAdapter;//定义适配器
    private RefreshLayout refreshLayout;

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
        refreshLayout = byView(R.id.recent_swipe);
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

        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);
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

    @Override
    public void onRefresh() {
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
    public void onLoad() {
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
                        refreshLayout.setLoading(false);
                    }

                    @Override
                    public void failure() {

                    }
                });
            }
        }).start();
    }
}
