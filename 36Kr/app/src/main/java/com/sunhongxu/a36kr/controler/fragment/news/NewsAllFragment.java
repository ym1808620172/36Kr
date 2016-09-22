package com.sunhongxu.a36kr.controler.fragment.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.NewsDetailsActivity;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.adapter.NewsAllAdapter;
import com.sunhongxu.a36kr.controler.adapter.RotateVpAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.IOpenDrawer;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 * 新闻界面Fragment
 */
public class NewsAllFragment extends AbsBaseFragment implements VolleyRequest, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private NewsAllAdapter allAdapter;//定义适配器
    private ListView listView;//dingyiListView
    private RotateVpAdapter vpAdapter;//定义轮播图适配器
    private ViewPager headerVp;//定义头布局ViewPager
    private LinearLayout pointLl;//定义轮播图小圆点样式
    private List<RotateNewsBean.DataBean.PicsBean> picsBeen;//定义轮播图数组
    private String string;//定义传过来的网址
    private ImageView titleNavigation, titlesActivity;//定义标题栏的图片
    private LinearLayout titles;//定义标题栏的布局
    private TextView titleTv;
    private ImageView searchImg;
    private IOpenDrawer iOpenDrawer;//定义接口,用于回调打开抽屉
    private SwipeRefreshLayout swipeLayout;//定义Swipe,下拉刷新
    private List<NewsAllBean.DataBean.DataBeans> dataBeanses;//定义数据数组


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //将接口赋值为context对象
        iOpenDrawer = (IOpenDrawer) context;
    }

    //Fragment的复用
    public static NewsAllFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putSerializable("url", url);
        NewsAllFragment fragment = new NewsAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_all_news;

    }

    //初始化组件
    @Override
    protected void initView() {
        swipeLayout = byView(R.id.swipe_container);
        listView = byView(R.id.news_all_listview);
        titleNavigation = byView(R.id.title_img_navigation);
        titlesActivity = byView(R.id.title_activity);
        titles = byView(R.id.root_title);
        titlesActivity.setVisibility(View.GONE);
        titleTv = byView(R.id.title_tv);
        searchImg = byView(R.id.title_search);
        searchImg.setOnClickListener(this);
        titleNavigation.setOnClickListener(this);

    }

    //加载数据
    @Override
    protected void initDatas() {
        //获取Bundle包裹化
        Bundle bundle = getArguments();
        //根据网址判断是哪个界面，是否添加头布局
        string = bundle.getString("url");
        //设置下拉刷新监听
        swipeLayout.setOnRefreshListener(this);
        //设施swipeLayout颜色
        swipeLayout.setColorSchemeResources(android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //初始化适配器
        allAdapter = new NewsAllAdapter(context, string);
        //listView绑定适配器
        listView.setAdapter(allAdapter);
        //Volley解析数据
        VolleyInstance.getInstance().startInstance(NetConstants.NEWSHELPER + string + NetConstants.NEWSURLEND, this);
        //设置Fragment外边距为电量栏高度
        titles.setPadding(0, MarginTop(), 0, 0);
        //根据url判断是哪个类型的新闻并设置标题文字
        setTitleTv();
        //定义ListView行点击事件
        listView.setOnItemClickListener(this);
    }

    //根据url判断是哪个类型的新闻并设置标题文字
    private void setTitleTv() {
        if (string.equals("all")) {
            titleTv.setText("全部");
            rotate();
        } else if (string.equals("67")) {
            titleTv.setText("早期项目");
        } else if (string.equals("68")) {
            titleTv.setText("B轮后期");
        } else if (string.equals("23")) {
            titleTv.setText("大公司");
        } else if (string.equals("69")) {
            titleTv.setText("资本");
        } else if (string.equals("70")) {
            titleTv.setText("深度");
        } else if (string.equals("71")) {
            titleTv.setText("研究");
        }
    }

    private void rotate() {
        //添加头布局
        View headerView = LayoutInflater.from(context).inflate(R.layout.item_header, null);
        listView.addHeaderView(headerView);
        headerVp = (ViewPager) headerView.findViewById(R.id.header_vp);
        pointLl = (LinearLayout) headerView.findViewById(R.id.rorate_point);
        //初始化适配器并绑定
        vpAdapter = new RotateVpAdapter(context);
        headerVp.setAdapter(vpAdapter);
        //网络请求数据
        VolleyInstance.getInstance().startInstance(NetConstants.ROTATEURL, new VolleyRequest() {
            @Override
            public void success(String result) {
                //Gson数据解析
                Gson gson = new Gson();
                RotateNewsBean rotateNewsBean = gson.fromJson(result, RotateNewsBean.class);
                picsBeen = rotateNewsBean.getData().getPics();
                //将数据设置到适配器
                vpAdapter.setDatas(picsBeen);
                //添加小圆点
                addPoint(picsBeen.size());
                //改变小圆点
                changePoint(picsBeen.size());
            }

            @Override
            public void failure() {

            }
        });
        handler = new Handler();
        //开始轮播
        startRotate();
    }


    private Handler handler;//定义handler
    private Runnable rotateRunnable;//定义Runnable
    private boolean isStart = false;//判断是否开始轮播

    //开始轮播
    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                //获得当前页
                int index = headerVp.getCurrentItem();
                //设置当前页并++
                headerVp.setCurrentItem(++index);
                if (isStart) {
                    handler.postDelayed(rotateRunnable, 3000);
                }
            }
        };
        handler.postDelayed(rotateRunnable, 3000);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        NewsAllBean datas = gson.fromJson(result, NewsAllBean.class);
        dataBeanses = datas.getData().getData();
        allAdapter.setDatas(dataBeanses);

    }

    //改变小圆点
    private void changePoint(final int size) {
        //ViewPage的滑动监听
        headerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据当前位置设置小圆点
                if (isStart) {
                    for (int i = 0; i < size; i++) {
                        ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                        pointIv.setImageResource(R.mipmap.lunpo_dangqian);
                    }
                    ImageView imageView = (ImageView) pointLl.getChildAt(position % size);
                    imageView.setImageResource(R.mipmap.lunpo);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //添加小圆点
    private void addPoint(int size) {
        for (int i = 0; i < size; i++) {
            ImageView pointIv = new ImageView(context);
            pointIv.setPadding(5, 5, 5, 5);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            pointIv.setLayoutParams(params);
            // 设置第0页小点的为灰色
            if (i == 0) {
                pointIv.setImageResource(R.mipmap.lunpo);
            } else {
                pointIv.setImageResource(R.mipmap.lunpo_dangqian);
            }
            pointLl.addView(pointIv);
        }
        Log.d("NewsAllFragment", "添加了");
    }

    @Override
    public void failure() {
    }

    //在界面生成时将状态设为true
    @Override
    public void onResume() {
        super.onResume();
        isStart = true;
    }

    //在界面暂停时设为false
    @Override
    public void onPause() {
        super.onPause();
        isStart = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_img_navigation:
                //用接口回调法打开抽屉
                iOpenDrawer.onIOpenDrawer(0);
                break;
            case R.id.title_search:
                //跳到搜索界面
                goTo(SearchActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                swipeLayout.setRefreshing(false);
                VolleyInstance.getInstance().startInstance(NetConstants.NEWSHELPER + string + NetConstants.NEWSURLEND, new VolleyRequest() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        NewsAllBean datas = gson.fromJson(result, NewsAllBean.class);
                        dataBeanses = datas.getData().getData();
                        Log.d("aaa", "dataBeanses.size():" + dataBeanses.size());
                        allAdapter.setDatas(dataBeanses);
                    }

                    @Override
                    public void failure() {

                    }
                });

            }
        }, 3000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取点击对应行的实体类数据
        NewsAllBean.DataBean.DataBeans dataBeans = (NewsAllBean.DataBean.DataBeans) parent.getItemAtPosition(position);
        String FeedId = dataBeans.getFeedId();
        String title = dataBeans.getTitle();
        Bundle bundle = new Bundle();
        bundle.putString("FeedId", FeedId);
        bundle.putString("title", title);
        //用ListView的行点击事件,跳转到详情页界面,将需要拼接的网址传过去
        goTo(NewsDetailsActivity.class, bundle);
    }
}
