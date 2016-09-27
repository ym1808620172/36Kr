package com.sunhongxu.a36kr.controler.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.AuthorRegionAdapter;
import com.sunhongxu.a36kr.model.bean.AuthorRegionBean;
import com.sunhongxu.a36kr.model.bean.NewsDetailsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;
import com.sunhongxu.a36kr.utils.ScrollViewListener;
import com.sunhongxu.a36kr.view.ObservableScrollView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 新闻详情界面
 */
public class NewsDetailsActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener, ScrollViewListener {
    private FrameLayout detailsLl;//定义根布局
    private WebView webView;//定义WebView
    private ImageView detailsCircleimg;//定义作者头像图片
    private TextView authorName;//定义作者名字
    private TextView contentAbstract;//内容摘要
    private ImageView iconDown;//定义弹出Pop的图片
    private LinearLayout authorContent;//定义作者资料的行布局
    private int totalCount,totalView;//定义总共浏览数和文章数,用在网络请求中
    private AuthorRegionAdapter adapter;//定义适配器,用在Pop
    private List<AuthorRegionBean.DataBean.LatestArticleBean> articleBeen;//用在Pop
    private boolean isopen = false;//判断是否打开了Pop
    private PopupWindow popupWindow;//Pop
    private TextView webTvTime;//文章发布时间
    private TextView webTvTitle;//文章标题
    private ImageView detailsBackImg;//返回按钮
    private ObservableScrollView scrollView;//定义ScrollView
    private LinearLayout toolbarLl;


    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_news_details;
    }

    //初始化组件
    @Override
    protected void initView() {
        detailsLl = byView(R.id.details_root);
        webView = byView(R.id.details_webview);
        detailsCircleimg = byView(R.id.details_circleimg);
        authorName = byView(R.id.author_name);
        contentAbstract = byView(R.id.content_abstract);
        iconDown = byView(R.id.icon_down);
        authorContent = byView(R.id.author_content);
        webTvTime = byView(R.id.webview_tv_time);
        webTvTitle = byView(R.id.webview_tv_title);
        detailsBackImg = byView(R.id.details_back_img);
        scrollView = byView(R.id.news_details_scroll);
        toolbarLl = byView(R.id.news_toolbar_ll);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //得到上个界面传过来的数据
        Intent intent = getIntent();
        if (intent != null) {
            //得到FeedId用于拼接网址
            String FeedId = intent.getStringExtra("FeedId");
            //得到title文章标题
            String title = intent.getStringExtra("title");
            //得到time文章发布时间
            String time = intent.getStringExtra("time");
            //设置时间
            webTvTime.setText(time);
            //设置标题
            webTvTitle.setText(title);
            //设置整体的内边距
            detailsLl.setPadding(0, MarginTop(), 10, 0);
            WebSettings webSettings = webView.getSettings();
            //设置WebView属性
            setWebSetting(webSettings);
            //获得屏幕的宽和高
            final int height = ScreenSizeConstants.getScreenSize(this, ScreenSizeConstants.ScreenState.HEIGHT);
            final int width = ScreenSizeConstants.getScreenSize(this, ScreenSizeConstants.ScreenState.WIDTH);
            //请求网络数据,新闻详情页的数据
            VolleyInstance.getInstance().startInstance(NetConstants.DETAILSURL + FeedId, this);
            //作者详情的数据
            AuthorDetails(FeedId, height, width);
            //设置监听
            setListener();
            //设置ScrollView的滑动监听
            scrollView.setScrollViewListener(this);
        }
    }
    //作者详情的数据
    private void AuthorDetails(String feedId, final int height, final int width) {
        VolleyInstance.getInstance().startInstance(NetConstants.AUTHORREGION + feedId + NetConstants.AUTHORREGIONEND, new VolleyRequest() {
            @Override
            public void success(String result) {
                //通过Gson解析
                Gson gson = new Gson();
                AuthorRegionBean authorRegionBean = gson.fromJson(result, AuthorRegionBean.class);
                //得到作者头像的网址
                String Avatar = authorRegionBean.getData().getAvatar();
                //用毕加索解析图片,并设置尺寸
                Picasso.with(NewsDetailsActivity.this).load(Avatar).resize(height / 10, width / 10).into(detailsCircleimg);
                //获得作者名字并设置
                String name = authorRegionBean.getData().getName();
                authorName.setText(name);
                //获得文章简介
                String Abstract = authorRegionBean.getData().getBrief();
                contentAbstract.setText(Abstract);
                totalCount = authorRegionBean.getData().getTotalCount();
                totalView = authorRegionBean.getData().getTotalView();
                articleBeen = authorRegionBean.getData().getLatestArticle();
            }

            @Override
            public void failure() {

            }
        });
    }
    //设置监听
    private void setListener() {
        authorContent.setOnClickListener(this);
        detailsBackImg.setOnClickListener(this);
    }

    @Override
    public void success(String result) {
        //解析文章详情数据,并显示在WebView上
        Gson gson = new Gson();
        NewsDetailsBean detailsBean = gson.fromJson(result, NewsDetailsBean.class);
        String content = detailsBean.getData().getContent();
        if (content != null) {
            webView.loadData(content, "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void failure() {

    }
    //设置监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.author_content:
                //当isopen为false时,说明pop没打开
                if (!isopen) {
                    //初始化Pop
                    popupWindow = new PopupWindow();
                    //设置宽高
                    popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
                    //加载自定义布局并设置数据,添加到Pop
                    View view = getLayoutInflater().inflate(R.layout.item_details_pop, null);
                    TextView contactAll = (TextView) view.findViewById(R.id.contact_all);
                    TextView browseAll = (TextView) view.findViewById(R.id.browse_all);
                    ListView listView = (ListView) view.findViewById(R.id.details_list);
                    adapter = new AuthorRegionAdapter(this);
                    listView.setAdapter(adapter);
                    adapter.setArticleBeen(articleBeen);
                    //文章数,和浏览数
                    contactAll.setText(totalCount + "");
                    browseAll.setText(totalView + "");
                    popupWindow.setContentView(view);
                    //设置图片
                    iconDown.setImageResource(R.mipmap.icon_up);
                    //将isopen设置true
                    isopen = true;
                    popupWindow.showAtLocation(view, Gravity.TOP, 0, MarginTop() + authorContent.getHeight());
                } else {
                    //将Pop关闭,并且将isopen的值设为false
                    iconDown.setImageResource(R.mipmap.icon_down);
                    popupWindow.dismiss();
                    isopen = false;
                }
                break;
            case R.id.details_back_img:
                finish();
                break;
        }
    }
    //ScrollView的滑动监听
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //获取屏幕高度
        int height = ScreenSizeConstants.getScreenSize(this,ScreenSizeConstants.ScreenState.HEIGHT);
        //当滑动的高度大于屏幕的高度时,导航栏隐藏
        if (y > height){
            toolbarLl.setVisibility(View.INVISIBLE);
        }else {
            toolbarLl.setVisibility(View.VISIBLE);
        }
    }
}
