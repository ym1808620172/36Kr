package com.sunhongxu.a36kr.controler.fragment.equity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.EquiteNewsAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.view.RefreshLayout;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 * 股权投资全部Fragment
 */
public class EquityAllFragment extends AbsBaseFragment implements VolleyRequest, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {

    private ListView listView;
    private EquiteNewsAdapter adapter;
    private com.sunhongxu.a36kr.view.RefreshLayout refreshLayout;
    private List<EquityBean.DataBean.DataBeans> datas;
    private String string;
    int a = 1;

    public static EquityAllFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putSerializable("URL", url);
        EquityAllFragment fragment = new EquityAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_quity_all;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.equity_list_view);
        refreshLayout = byView(R.id.quity_swipe);

    }

    @Override
    protected void initDatas() {
        adapter = new EquiteNewsAdapter(context);
        listView.setAdapter(adapter);
        Bundle bundle = getArguments();
        string = bundle.getString("URL");
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        VolleyInstance.getInstance().startInstance(NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND, this);
    }


    @Override
    public void success(String result) {
        Gson gson = new Gson();
        EquityBean bean = gson.fromJson(result, EquityBean.class);
        datas = bean.getData().getDatas();
        adapter.setDatas(datas);
    }

    @Override
    public void failure() {

    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                VolleyInstance.getInstance().startInstance(NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        EquityBean equityBean = gson.fromJson(result, EquityBean.class);
                        datas = equityBean.getData().getDatas();
                        adapter.setDatas(datas);
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
        a++;
        Log.d("xxx", "a:" + a);
        final String URL = NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND;
        StringBuffer buffer = new StringBuffer();
        buffer.append(URL);
        int index = buffer.indexOf("20");
        int more = 0 , how= 20;
        for (int i = 0; i < a; i++) {
            how = how+10;
        }
        more = how;
        String end = String.valueOf(more);
        final String endUrl = String.valueOf(buffer.replace(index,index+2,end));
        new Thread(new Runnable() {
            @Override
            public void run() {
                VolleyInstance.getInstance().startInstance(endUrl, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        EquityBean equityBean = gson.fromJson(result, EquityBean.class);
                        datas = equityBean.getData().getDatas();
                        adapter.setDatas(datas);
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
