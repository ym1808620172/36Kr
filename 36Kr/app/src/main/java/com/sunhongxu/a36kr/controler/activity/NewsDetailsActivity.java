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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 新闻详情界面
 */
public class NewsDetailsActivity extends AbsBaseActivity implements VolleyRequest, View.OnClickListener {
    private FrameLayout detailsLl;//定义根布局
    private WebView webView;
    private ImageView detailsCircleimg;
    private TextView authorName;
    private TextView contentAbstract;
    private ImageView iconDown;
    private LinearLayout authorContent;
    private int totalCount;
    private int totalView;
    private AuthorRegionAdapter adapter;
    private List<AuthorRegionBean.DataBean.LatestArticleBean> articleBeen;
    private boolean isopen = false;
    private PopupWindow popupWindow;
    private TextView webTvTime;
    private TextView webTvTitle;

    @Override
    protected void onResume() {
        super.onResume();
    }

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
        iconDown = byView(R.id.icon_down);
        authorContent = byView(R.id.author_content);
        webTvTime = byView(R.id.webview_tv_time);
        webTvTitle = byView(R.id.webview_tv_title);
    }

    @Override
    protected void initDatas() {
        Intent intent = getIntent();
        String FeedId = intent.getStringExtra("FeedId");
        String title = intent.getStringExtra("title");
        webTvTitle.setText(title);
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
        final int height = ScreenSizeConstants.getScreenSize(this, ScreenSizeConstants.ScreenState.HEIGHT);
        final int width = ScreenSizeConstants.getScreenSize(this, ScreenSizeConstants.ScreenState.WIDTH);
        VolleyInstance.getInstance().startInstance(NetConstants.DETAILSURL + FeedId, this);
        VolleyInstance.getInstance().startInstance(NetConstants.AUTHORREGION + FeedId + NetConstants.AUTHORREGIONEND, new VolleyRequest() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                AuthorRegionBean authorRegionBean = gson.fromJson(result, AuthorRegionBean.class);
                String Avatar = authorRegionBean.getData().getAvatar();
                Picasso.with(NewsDetailsActivity.this).load(Avatar).resize(height / 10, width / 10).into(detailsCircleimg);
                String name = authorRegionBean.getData().getName();
                authorName.setText(name);
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
        authorContent.setOnClickListener(this);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        NewsDetailsBean detailsBean = gson.fromJson(result, NewsDetailsBean.class);
        String content = detailsBean.getData().getContent();
        if (content != null) {
            webView.loadData(content, "text/html; charset=UTF-8", null);
            long time = detailsBean.getData().getPublishTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = new Date(time);
            String fromatTimt = sdf.format(date);
            webTvTime.setText(fromatTimt);
        }
    }

    @Override
    public void failure() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.author_content:
                if (!isopen) {
                    popupWindow = new PopupWindow();
                    popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
                    View view = getLayoutInflater().inflate(R.layout.item_details_pop, null);
                    TextView contactAll = (TextView) view.findViewById(R.id.contact_all);
                    TextView browseAll = (TextView) view.findViewById(R.id.browse_all);
                    ListView listView = (ListView) view.findViewById(R.id.details_list);
                    adapter = new AuthorRegionAdapter(this);
                    listView.setAdapter(adapter);
                    Log.d("xxx", "articleBeen:" + articleBeen);
                    adapter.setArticleBeen(articleBeen);
                    contactAll.setText(totalCount + "");
                    browseAll.setText(totalView + "");
                    popupWindow.setContentView(view);
                    iconDown.setImageResource(R.mipmap.icon_up);
                    isopen = true;
                    popupWindow.showAtLocation(view, Gravity.TOP, 0, MarginTop() + authorContent.getHeight());
                } else {
                    Log.d("xxx", "执行了");
                    iconDown.setImageResource(R.mipmap.icon_down);
                    popupWindow.dismiss();
                    isopen = false;
                }
                break;
        }
    }
}
