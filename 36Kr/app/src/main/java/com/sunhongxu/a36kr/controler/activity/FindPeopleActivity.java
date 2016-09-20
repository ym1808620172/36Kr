package com.sunhongxu.a36kr.controler.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.FindPeopleAdapter;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.DiscoverNetConstants;
import com.sunhongxu.a36kr.utils.EquityNetConstants;

import java.util.List;

public class FindPeopleActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private ListView listView;
    private FindPeopleAdapter peopleAdapter;
    private LinearLayout rootTitle;
    private ImageView backImg;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected int setLayout() {
        return R.layout.activity_find_people;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.find_people_list);
        backImg = byView(R.id.find_people_back_img);
        ImageView imageViewTitle = byView(R.id.visibity);
        imageViewTitle.setVisibility(View.INVISIBLE);
        rootTitle = byView(R.id.find_people_root);
        refreshLayout = byView(R.id.find_people_swipe);
        backImg.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        peopleAdapter = new FindPeopleAdapter(this);
        listView.setAdapter(peopleAdapter);
        VolleyInstance.getInstance().startInstance(DiscoverNetConstants.FINDPEOPLE, this);
        rootTitle.setPadding(0, MarginTop(), 0, 0);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(android.R.color.holo_blue_light);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        FindPeopleBean peopleBean = gson.fromJson(result, FindPeopleBean.class);
        List<FindPeopleBean.DataBean.DataBeans> dataBeanses = peopleBean.getData().getDatas();
        peopleAdapter.setDataBeanses(dataBeanses);
    }

    @Override
    public void failure() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_people_back_img:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                refreshLayout.setRefreshing(false);
                VolleyInstance.getInstance().startInstance(DiscoverNetConstants.FINDPEOPLE, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        FindPeopleBean peopleBean = gson.fromJson(result, FindPeopleBean.class);
                        List<FindPeopleBean.DataBean.DataBeans> dataBeanses = peopleBean.getData().getDatas();
                        peopleAdapter.setDataBeanses(dataBeanses);
                    }

                    @Override
                    public void failure() {

                    }
                });

            }
        },3000);
    }
}
