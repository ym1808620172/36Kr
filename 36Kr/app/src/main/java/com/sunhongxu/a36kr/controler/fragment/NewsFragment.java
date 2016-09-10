package com.sunhongxu.a36kr.controler.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/8.
 * 新闻界面的Fragment
 *
 * @author sunhongxu
 */
public class NewsFragment extends AbsBaseFragment {
    private FrameLayout frameLayout;
    private Button textView;

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    //初始化控件
    @Override
    protected void initView() {
        frameLayout = byView(R.id.framelayout_news);
        textView = byView(R.id.news_tv);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //设置Fragment外边距为电量栏高度
        frameLayout.setPadding(0, MarginTop(), 0, 0);
        //发广播
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.sunhongxu.a36kr.controler.fragment.NewsFragment");
                context.sendBroadcast(intent);
            }
        });
        FragmentManager manager = getChildFragmentManager();
        //设置默认选中页
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout_news, new NewsAllFragment());
        transaction.commit();
    }
}
