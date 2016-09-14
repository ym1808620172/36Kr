package com.sunhongxu.a36kr.controler.fragment;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.RotateVpAdapter;
import com.sunhongxu.a36kr.model.bean.DiscoverRotateBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.DiscoverNetUtils;
import com.sunhongxu.a36kr.utils.ScrollViewListener;
import com.sunhongxu.a36kr.view.ObservableScrollView;

import java.util.List;


/**
 * Created by dllo on 16/9/8.
 */
public class DiscoveryFragment extends AbsBaseFragment implements VolleyRequest, ScrollViewListener {

    private ViewPager discoverVp;
    private RotateVpAdapter rotateVpAdapter;
    private Handler handler;
    private Runnable rotateRunnable;
    private boolean isStart = false;
    private LinearLayout discoverPoint;
    private ImageView searchImg;
    private ObservableScrollView observable;

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
        observable.setScrollViewListener(this);
    }

    @Override
    protected void initDatas() {

        searchImg.setPadding(10, MarginTop() + 10, 10, 10);
        rotateVpAdapter = new RotateVpAdapter(context);
        discoverVp.setAdapter(rotateVpAdapter);
        VolleyInstance.getInstance().startInstance(DiscoverNetUtils.DISCOVERROTATE, this);
        handler = new Handler();
        startRotate();
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
}
