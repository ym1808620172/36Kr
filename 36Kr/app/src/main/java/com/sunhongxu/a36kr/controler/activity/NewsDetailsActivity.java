package com.sunhongxu.a36kr.controler.activity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.NewsDetailsBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.net.URL;

/**
 * 新闻详情界面
 */
public class NewsDetailsActivity extends AbsBaseActivity implements VolleyRequest {
    private FrameLayout detailsLl;//定义根布局
    private WebView webView;
    private ImageView detailsCircleimg;
    private TextView authorName;
    private TextView contentAbstract;

    @Override
    protected int setLayout() {
        return R.layout.activity_news_details;
    }

    @Override
    protected void initView() {
        detailsLl = byView(R.id.details_root);
        webView = byView(R.id.details_webview);
        detailsCircleimg = byView(R.id.details_circleimg);
        authorName = byView(R.id.author_name);
        contentAbstract = byView(R.id.content_abstract);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        String FeedId = intent.getStringExtra("FeedId");
        detailsLl.setPadding(0, MarginTop(), 10, 0);
        WebSettings webSettings = webView.getSettings();
        //让WebView可以执行JavaScript
        webSettings.setJavaScriptEnabled(true);
        //让JavaScript可以自定打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置可以缓存
        webSettings.setAppCacheEnabled(true);
        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置缓存路径
        webSettings.setAppCachePath("");
        //设置屏幕自适应
        webSettings.setSupportZoom(false);
        //设置图片自适应
        webSettings.setUseWideViewPort(false);
        //支持内容重新布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //设置屏幕可控
        webSettings.setDisplayZoomControls(true);
        VolleyInstance.getInstance().startInstance(NetConstants.DETAILSURL + FeedId, this);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        NewsDetailsBean detailsBean = gson.fromJson(result, NewsDetailsBean.class);
        String content = detailsBean.getData().getContent();
        if (content!=null){
            webView.loadData(content,"text/html; charset=UTF-8",null);
        }
    }
    @Override
    public void failure() {

    }
}
