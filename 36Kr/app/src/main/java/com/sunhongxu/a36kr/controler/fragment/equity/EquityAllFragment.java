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

    private ListView listView;//定义ListView
    private EquiteNewsAdapter adapter;//定义适配器
    private com.sunhongxu.a36kr.view.RefreshLayout refreshLayout;//定义RefreshLayout
    private List<EquityBean.DataBean.DataBeans> datas;//定义数组
    private String string;//定义一个String,用与赋值传过来的数据,网址拼接
    int a = 1;//设置上拉加载次数,默认为1
    //Fragment的复用
    public static EquityAllFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putSerializable("URL", url);
        EquityAllFragment fragment = new EquityAllFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_quity_all;
    }
    //初始化组件
    @Override
    protected void initView() {
        listView = byView(R.id.equity_list_view);
        refreshLayout = byView(R.id.quity_swipe);

    }
    //加载数据
    @Override
    protected void initDatas() {
        //初始化适配器
        adapter = new EquiteNewsAdapter(context);
        //ListView绑定设配器
        listView.setAdapter(adapter);
        //获得bundle的值
        Bundle bundle = getArguments();
        string = bundle.getString("URL");
        //设置下拉刷新监听
        refreshLayout.setOnRefreshListener(this);
        //设置上拉加载监听
        refreshLayout.setOnLoadListener(this);
        //设置下拉刷新小圆圈的颜色
        refreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //请求网络数据
        VolleyInstance.getInstance().startInstance(NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND, this);
    }


    @Override
    public void success(String result) {
        //数据解析
        Gson gson = new Gson();
        EquityBean bean = gson.fromJson(result, EquityBean.class);
        datas = bean.getData().getDatas();
        adapter.setDatas(datas);
    }

    @Override
    public void failure() {

    }
    //下拉刷新
    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //重新请求网络数据并解析传入适配器
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
    //上啦加载
    @Override
    public void onLoad() {
        //定义网址
        final String URL = NetConstants.EQUITYHELPER + string + NetConstants.EQUITYHELPEREND;
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
