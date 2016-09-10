package com.sunhongxu.a36kr.controler.fragment;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.fragment.news.NewsAllFragment;

/**
 * Created by dllo on 16/9/8.
 * 新闻界面的Fragment
 *
 * @author sunhongxu
 */
public class NewsFragment extends AbsBaseFragment {
    private FrameLayout frameLayout;
    private ImageView titleNavigation, titlesActivity;
    private LinearLayout titles;
    private TextView titleTv;
    private ImageView searchImg;


    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    //初始化控件
    @Override
    protected void initView() {
        frameLayout = byView(R.id.framelayout_news);
        titleNavigation = byView(R.id.title_img_navigation);
        titlesActivity = byView(R.id.title_activity);
        titles = byView(R.id.root_title);
        titlesActivity.setVisibility(View.GONE);
        titleTv = byView(R.id.title_tv);
        searchImg = byView(R.id.title_search);
    }

    //加载数据
    @Override
    protected void initDatas() {

        getChildFragmentManager().beginTransaction().replace(R.id.framelayout_news, new NewsAllFragment()).commit();

        //设置Fragment外边距为电量栏高度
        titles.setPadding(0, MarginTop(), 0, 0);
        //发广播
        titleNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("com.sunhongxu.a36kr.controler.fragment.NewsFragment");
                context.sendBroadcast(intent);
            }
        });


    }


}
