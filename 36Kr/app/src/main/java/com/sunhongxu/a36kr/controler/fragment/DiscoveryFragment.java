package com.sunhongxu.a36kr.controler.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.FindPersonActivity;
import com.sunhongxu.a36kr.controler.activity.RecentAtyActivity;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.activity.StudyActivity;
import com.sunhongxu.a36kr.controler.adapter.RotateVpAdapter;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import com.sunhongxu.a36kr.utils.IOpenDrawer;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;
import com.sunhongxu.a36kr.utils.ScrollViewListener;
import com.sunhongxu.a36kr.view.ObservableScrollView;

import java.util.List;


/**
 * Created by dllo on 16/9/8.
 */
public class DiscoveryFragment extends AbsBaseFragment implements VolleyRequest, ScrollViewListener, View.OnClickListener {

    private ViewPager discoverVp;//定义发现界面的ViewPager,用于轮播图
    private RotateVpAdapter rotateVpAdapter;//定义轮播图适配器
    private Handler handler;//定义Handler
    private Runnable rotateRunnable;
    private boolean isStart = false;//设置开始状态为false
    private LinearLayout discoverPoint;//定义小圆点布局
    private ImageView searchImg;//定义搜索图片
    private ObservableScrollView observable;//定义自定义ScrollView
    private LinearLayout studyResearch;
    private LinearLayout activityBtn;
    private TextView checkAll;
    private ImageView discoverSearch;
    private ImageView hotProgectImg;
    private TextView hotProgectName;
    private TextView hotProgectBrief;
    private LinearLayout findEquity;
    private IOpenDrawer iOpenDrawer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iOpenDrawer = (IOpenDrawer) context;
    }

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        return fragment;
    }

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_discovery;
    }

    //初始化组件
    @Override
    protected void initView() {
        discoverVp = byView(R.id.discover_vp);
        discoverPoint = byView(R.id.discover_point);
        searchImg = byView(R.id.discover_search);
        observable = byView(R.id.discover_root_scroll);
        studyResearch = byView(R.id.study_research);
        activityBtn = byView(R.id.activity_btn);
        checkAll = byView(R.id.check_all);
        discoverSearch = byView(R.id.discover_search);
        hotProgectImg = byView(R.id.hot_project_img);
        hotProgectName = byView(R.id.hot_project_name);
        hotProgectBrief = byView(R.id.hot_project_brief);
        findEquity = byView(R.id.discover_find_equity);
        //设置监听
        setListener();

    }

    //设置监听
    private void setListener() {
        checkAll.setOnClickListener(this);
        studyResearch.setOnClickListener(this);
        activityBtn.setOnClickListener(this);
        observable.setScrollViewListener(this);
        discoverSearch.setOnClickListener(this);
        findEquity.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {

        searchImg.setPadding(10, MarginTop() + 10, 10, 10);
        rotateVpAdapter = new RotateVpAdapter(context);
        discoverVp.setAdapter(rotateVpAdapter);
        VolleyInstance.getInstance().startInstance(NetConstants.DISCOVERROTATE, this);
        handler = new Handler();
        //热门项目,获取股权投资界面数据的第一个
        hotProgect();
        //开始轮播
        startRotate();
    }

    private void hotProgect() {
        VolleyInstance.getInstance().startInstance(NetConstants.EQUITYHELPER + "underway" + NetConstants.EQUITYHELPEREND, new VolleyRequest() {
            @Override
            public void success(String result) {
                int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
                int weight = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
                Gson gson = new Gson();
                EquityBean equityBean = gson.fromJson(result, EquityBean.class);
                List<EquityBean.DataBean.DataBeans> dataBeanses = equityBean.getData().getData();
                EquityBean.DataBean.DataBeans dataBeans = dataBeanses.get(0);
                Picasso.with(context).load(dataBeans.getCompany_logo()).resize(weight / 6, height / 6).into(hotProgectImg);
                hotProgectName.setText("创始人:" + dataBeans.getCompany_name());
                hotProgectBrief.setText(dataBeans.getCompany_brief());
            }

            @Override
            public void failure() {

            }
        });

    }

    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int index = discoverVp.getCurrentItem();
                discoverVp.setCurrentItem(++index);
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
        RotateNewsBean discoverRotateBean = gson.fromJson(result, RotateNewsBean.class);
        List<RotateNewsBean.DataBean.PicsBean> picsBeen = discoverRotateBean.getData().getPics();
        rotateVpAdapter.setDatas(picsBeen);
        //添加小圆点
        addPoint(picsBeen.size());
        //改变小圆点
        changePoint(picsBeen.size());

    }

    //改变小圆点
    private void changePoint(final int size) {
        discoverVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (isStart) {
                    for (int i = 0; i < size; i++) {
                        ImageView pointIv = (ImageView) discoverPoint.getChildAt(i);
                        pointIv.setImageResource(R.mipmap.lunpo_dangqian);
                    }
                    ImageView imageView = (ImageView) discoverPoint.getChildAt(position % size);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
            pointIv.setLayoutParams(params);
            // 设置第0页小点的为灰色
            if (i == 0) {
                pointIv.setImageResource(R.mipmap.lunpo);
            } else {
                pointIv.setImageResource(R.mipmap.lunpo_dangqian);
            }
            discoverPoint.addView(pointIv);
        }
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

    //ScrollView的滑动监听
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //当滑动高度小与轮播图高度时候设置搜索按钮为可见
        if (discoverVp.getHeight() <= y) {
            searchImg.setVisibility(View.INVISIBLE);
        } else {
            searchImg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.study_research:
                goTo(StudyActivity.class);
                break;
            case R.id.activity_btn:
                goTo(RecentAtyActivity.class);
                break;
            case R.id.check_all:
                iOpenDrawer.onIOpenDrawer(1);
                break;
            case R.id.discover_search:
                goTo(SearchActivity.class);
                break;
            case R.id.discover_find_equity:
                goTo(FindPersonActivity.class);
                break;
        }
    }
}
