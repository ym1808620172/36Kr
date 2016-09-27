package com.sunhongxu.a36kr.controler.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.FindPeopleAdapter;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.view.RefreshLayout;

import java.util.List;

/**
 * 寻找投资人界面
 */
public class FindPersonActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, RefreshLayout.OnLoadListener {


    private ListView listView;//定义ListView
    private FindPeopleAdapter peopleAdapter;//定义适配器
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回图片
    private com.sunhongxu.a36kr.view.RefreshLayout refreshLayout;//定义下拉刷新
    int a = 1;//上拉加载的次数,默认为1

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
        TextView imageViewTitle = byView(R.id.visibity);
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
        //为上拉加载设置监听
        refreshLayout.setOnLoadListener(this);
        //设置下拉刷新颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_people_back_img:
                //关闭本界面
                finish();
                break;
        }
    }
    //下拉刷新
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //网络请求数据
                VolleyInstance.getInstance().startInstance(NetConstants.FINDPEOPLE, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        //数据解析并传入适配器
                        Gson gson = new Gson();
                        FindPeopleBean peopleBean = gson.fromJson(result, FindPeopleBean.class);
                        List<FindPeopleBean.DataBean.DataBeans> dataBeanses = peopleBean.getData().getDatas();
                        peopleAdapter.setDataBeanses(dataBeanses);
                        refreshLayout.setRefreshing(false);
                    }
                    @Override
                    public void failure() {

                    }
                });
            }
        }).start();
    }
    //上啦加载
    @Override
    public void onLoad() {
        //定义网址
        final String URL = NetConstants.FINDPEOPLE;
        //定义StringBuffer,字符串查询等
        StringBuffer buffer = new StringBuffer();
        //添加数据
        buffer.append(URL);
        //查找20在的位置,20为加载多少了条数据,加载就让他+10
        int index = buffer.indexOf("20");
        //定义加载了多少条数据
        int more = 0, how = 20;
        for (int i = 0; i < a; i++) {
            //上啦几次,加几个10
            how = how + 10;
        }
        //将how赋给另一个参数
        more = how;
        //转为String类型
        String end = String.valueOf(more);
        //20替换为改变后的数
        final String endUrl = String.valueOf(buffer.replace(index,index+2,end));
        new Thread(new Runnable() {
            @Override
            public void run() {
                //用新的网址请求数据并解析
                VolleyInstance.getInstance().startInstance(endUrl, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        FindPeopleBean peopleBean = gson.fromJson(result, FindPeopleBean.class);
                        List<FindPeopleBean.DataBean.DataBeans> dataBeanses = peopleBean.getData().getDatas();
                        peopleAdapter.setDataBeanses(dataBeanses);
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
