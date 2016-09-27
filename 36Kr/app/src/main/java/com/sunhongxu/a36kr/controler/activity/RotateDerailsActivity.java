package com.sunhongxu.a36kr.controler.activity;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;

/**
 * 轮播图详情页
 */

public class RotateDerailsActivity extends AbsBaseActivity {


    private WebView webView;//定义WebView
    private LinearLayout linearLayout;//定义根布局

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.activity_rotate_derails;
    }

    //初始化组件
    @Override
    protected void initView() {
        webView = byView(R.id.rotate_web);
        linearLayout = byView(R.id.rotate_root_aty);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //设置布局的内边距
        linearLayout.setPadding(0,MarginTop(),0,0);
        //获得传过来的网址
        Intent intent = getIntent();
        if (intent!=null){
            //如果intent不为空,获得网址
            String URL = intent.getStringExtra("URL");
            //设置WebView属性
            WebSettings webSettings = webView.getSettings();
            setWebSetting(webSettings);
            webView.loadUrl(URL);
        }
    }
}
