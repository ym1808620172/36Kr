package com.sunhongxu.a36kr.controler.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.FindPeopleAdapter;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * 寻找投资人界面
 */
public class FindPersonActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private ListView listView;//定义ListView
    private FindPeopleAdapter peopleAdapter;//定义适配器
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回图片
    private SwipeRefreshLayout refreshLayout;//定义下拉刷新

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_find_person;
    }

    //初始化组价
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
        //初始化适配器并绑定
        peopleAdapter = new FindPeopleAdapter(this);
        listView.setAdapter(peopleAdapter);
        //数据解析
        VolleyInstance.getInstance().startInstance(NetConstants.FINDPEOPLE, this);
        //设置标题栏与上边界距离为电量栏高度
        rootTitle.setPadding(0, MarginTop(), 0, 0);
        //为下拉刷新设置监听
        refreshLayout.setOnRefreshListener(this);
        //设置下拉刷新颜色
        refreshLayout.setColorSchemeColors(android.R.color.holo_blue_light);
    }

    //数据解析
    @Override
    public void success(String result) {
        //Gson解析数据并传入适配器
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
                //关闭本界面
                finish();
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                refreshLayout.setRefreshing(false);
                VolleyInstance.getInstance().startInstance(NetConstants.FINDPEOPLE, new VolleyRequest() {
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
