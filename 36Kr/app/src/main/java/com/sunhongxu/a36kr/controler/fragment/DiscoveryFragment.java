package com.sunhongxu.a36kr.controler.fragment;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.FindPeopleActivity;
import com.sunhongxu.a36kr.controler.activity.MainActivity;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.activity.StudyActivity;
import com.sunhongxu.a36kr.controler.adapter.RotateVpAdapter;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.DiscoverNetConstants;
import com.sunhongxu.a36kr.utils.EquityNetConstants;
import com.sunhongxu.a36kr.utils.NewsNetConstants;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;
import com.sunhongxu.a36kr.utils.ScrollViewListener;
import com.sunhongxu.a36kr.view.ObservableScrollView;

import java.util.List;


/**
 * Created by dllo on 16/9/8.
 */
public class DiscoveryFragment extends AbsBaseFragment implements VolleyRequest, ScrollViewListener, View.OnClickListener {

    private ViewPager discoverVp;
    private RotateVpAdapter rotateVpAdapter;
    private Handler handler;
    private Runnable rotateRunnable;
    private boolean isStart = false;
    private LinearLayout discoverPoint;
    private ImageView searchImg;
    private ObservableScrollView observable;
    private LinearLayout studyResearch;
    private LinearLayout activityBtn;
    private TextView checkAll;
    private ImageView discoverSearch;
    private ImageView hotProgectImg;
    private TextView hotProgectName;
    private TextView hotProgectBrief;
    private LinearLayout findEquity;

    public static DiscoveryFragment newInstance() {
        DiscoveryFragment fragment = new DiscoveryFragment();
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_discovery;
    }

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
        VolleyInstance.getInstance().startInstance(DiscoverNetConstants.DISCOVERROTATE, this);
        handler = new Handler();
        VolleyInstance.getInstance().startInstance(EquityNetConstants.EQUITYHELPER + "underway" + EquityNetConstants.EQUITYHELPEREND, new VolleyRequest() {
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
        addPoint(picsBeen.size());
        changePoint(picsBeen.size());

    }

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

    @Override
    public void onResume() {
        super.onResume();
        isStart = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isStart = false;
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
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
                break;
            case R.id.check_all:
                break;
            case R.id.discover_search:
                goTo(SearchActivity.class);
                break;
            case R.id.discover_find_equity:
                goTo(FindPeopleActivity.class);
                break;
        }
    }
}
