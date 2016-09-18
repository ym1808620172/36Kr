package com.sunhongxu.a36kr.controler.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.NewsAllAdapter;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NewsNetConstants;

import java.util.List;

public class StudyActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener {
    private NewsAllAdapter allAdapter;
    private ListView listView;
    private ImageView titleSearch;
    private ImageView titleAty;
    private ImageView titleImgNavigation;
    private TextView titleTv;
    private LinearLayout titleLl;

    @Override
    protected int setLayout() {
        return R.layout.fragment_all_news;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.news_all_listview);
        titleImgNavigation = byView(R.id.title_img_navigation);
        titleAty = byView(R.id.title_activity);
        titleSearch = byView(R.id.title_search);
        titleTv = byView(R.id.title_tv);
        titleLl = byView(R.id.root_title);
        titleImgNavigation.setOnClickListener(this);

    }

    @Override
    protected void initDatas() {
        allAdapter = new NewsAllAdapter(this);
        VolleyInstance.getInstance().startInstance(NewsNetConstants.NEWSHELPER + "71" + NewsNetConstants.NEWSURLEND, this);
        listView.setAdapter(allAdapter);
        //设置标题栏
        setTitles();

    }

    private void setTitles() {
        titleTv.setText("36氪研究院");
        titleImgNavigation.setImageResource(R.mipmap.news_toolbar_icon_back);
        titleAty.setVisibility(View.INVISIBLE);
        titleSearch.setVisibility(View.INVISIBLE);
        titleLl.setPadding(0, MarginTop(), 0, 0);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        NewsAllBean datas = gson.fromJson(result, NewsAllBean.class);
        List<NewsAllBean.DataBean.DataBeans> dataBeanses = datas.getData().getData();
        allAdapter.setDatas(dataBeanses);
    }

    @Override
    public void failure() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_img_navigation:
                finish();
                break;
        }
    }
}
