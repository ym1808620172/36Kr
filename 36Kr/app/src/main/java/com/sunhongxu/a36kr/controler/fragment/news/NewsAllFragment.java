package com.sunhongxu.a36kr.controler.fragment.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.NewsAllAdapter;
import com.sunhongxu.a36kr.controler.adapter.RotateVpAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NewsNetUtils;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 */
public class NewsAllFragment extends AbsBaseFragment implements VolleyRequest {

    private NewsAllAdapter allAdapter;
    private ListView listView;
    private RotateVpAdapter vpAdapter;
    private ViewPager headerVp;
    private LinearLayout pointLl;
    private List<RotateNewsBean.DataBean.PicsBean> picsBeen;
    private String string;


    public static NewsAllFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putSerializable("url", url);
        NewsAllFragment fragment = new NewsAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_all_news;

    }

    @Override
    protected void initView() {
        listView = byView(R.id.news_all_listview);

    }

    @Override
    protected void initDatas() {
        allAdapter = new NewsAllAdapter(context);
        listView.setAdapter(allAdapter);
        Bundle bundle = getArguments();
        string = bundle.getString("url");
        if (string.equals("all")) {
            rotate();
        }
        VolleyInstance.getInstance().startInstance(NewsNetUtils.NEWSHELPER + string + NewsNetUtils.NEWSURLEND, this);
    }

    private void rotate() {
        View headerView = LayoutInflater.from(context).inflate(R.layout.item_header, null);
        listView.addHeaderView(headerView);
        headerVp = (ViewPager) headerView.findViewById(R.id.header_vp);
        pointLl = (LinearLayout) headerView.findViewById(R.id.rorate_point);
        vpAdapter = new RotateVpAdapter(context);
        headerVp.setAdapter(vpAdapter);
        StringRequest request = new StringRequest(NewsNetUtils.ROTATEURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RotateNewsBean rotateNewsBean = gson.fromJson(response, RotateNewsBean.class);
                picsBeen = rotateNewsBean.getData().getPics();
                vpAdapter.setDatas(picsBeen);
                Log.d("NewsAllFragment", "picsBeen.size():" + picsBeen.size());
                //添加小圆点
                addPoint(picsBeen.size());
                //改变小圆点
                changePoint(picsBeen.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
        //开始轮播
        handler = new Handler();
        startRotate();
    }


    private Handler handler;
    private Runnable rotateRunnable;
    private boolean isStart = false;

    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int index = headerVp.getCurrentItem();
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
        List<NewsAllBean.DataBean.DataBeans> dataBeanses = datas.getData().getData();
        allAdapter.setDatas(dataBeanses);
        Log.d("NewsAllFragment", string);

    }

    private void changePoint(final int size) {
        headerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
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
}
