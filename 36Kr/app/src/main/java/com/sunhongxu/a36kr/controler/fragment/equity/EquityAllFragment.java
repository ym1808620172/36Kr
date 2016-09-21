package com.sunhongxu.a36kr.controler.fragment.equity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.EquiteNewsAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 * 全部Fragment
 */
public class EquityAllFragment extends AbsBaseFragment implements VolleyRequest, SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private EquiteNewsAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private List<EquityBean.DataBean.DataBeans> datas;
    private String string;

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
        datas = bean.getData().getData();
        adapter.setDatas(datas);
    }

    @Override
    public void failure() {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                refreshLayout.setRefreshing(false);
                VolleyInstance.getInstance().startInstance(NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        EquityBean equityBean = gson.fromJson(result, EquityBean.class);
                        datas = equityBean.getData().getData();
                        adapter.setDatas(datas);
                    }

                    @Override
                    public void failure() {

                    }
                });

            }
        },3000);
    }
}
