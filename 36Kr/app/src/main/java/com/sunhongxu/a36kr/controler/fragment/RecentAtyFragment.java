package com.sunhongxu.a36kr.controler.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.RotateDerailsActivity;
import com.sunhongxu.a36kr.controler.adapter.RecentAtyAdapter;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * Created by dllo on 16/9/28.
 */
public class RecentAtyFragment extends AbsBaseFragment implements VolleyRequest, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private ListView recentList;//定义ListView
    private RecentAtyAdapter atyAdapter;//定义适配器
    private com.sunhongxu.a36kr.view.RefreshLayout refreshLayout;//定义RefreshLayout下拉刷新

    public static RecentAtyFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("URL", url);
        RecentAtyFragment fragment = new RecentAtyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_recent_aty;
    }

    @Override
    protected void initView() {
        recentList = byView(R.id.recent_list_view);
        refreshLayout = byView(R.id.recent_swipe);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getArguments();
        String URL = bundle.getString("URL");
        //初始化适配器并绑定
        atyAdapter = new RecentAtyAdapter(context);
        recentList.setAdapter(atyAdapter);
        //网络请求数据
        VolleyInstance.getInstance().startInstance(NetConstants.RECENTATY + URL , this);
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
        goTo(RotateDerailsActivity.class, bundle);
    }
}
