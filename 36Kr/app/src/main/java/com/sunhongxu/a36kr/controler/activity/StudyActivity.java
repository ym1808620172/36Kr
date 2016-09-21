package com.sunhongxu.a36kr.controler.activity;


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
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.List;

/**
 * 36氪研究院界面
 */

public class StudyActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener {
    private NewsAllAdapter allAdapter;//定义适配器
    private ListView listView;//定义ListView
    private ImageView titleSearch;//定义搜索图片
    private ImageView titleAty;//定义活动图片
    private ImageView titleImgNavigation;//定义返回图片
    private TextView titleTv;//定义标题TextView
    private LinearLayout titleLl;//定义标题布局

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_all_news;
    }

    //初始化组件
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

    //加载数据
    @Override
    protected void initDatas() {
        //设置适配器并绑定,第二个参数用于判断是不是全部新闻的界面,新闻类型是否显示
        allAdapter = new NewsAllAdapter(this, null);
        VolleyInstance.getInstance().startInstance(NetConstants.NEWSHELPER + "71" + NetConstants.NEWSURLEND, this);
        listView.setAdapter(allAdapter);
        //设置标题栏
        setTitles();

    }
    //设置标题栏
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
