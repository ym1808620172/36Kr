package com.sunhongxu.a36kr.controler.fragment;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private DrawerLayout drawerLayout;
    private LinearLayout linearLayout;

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
        drawerLayout = byView(R.id.drawerLayout);
        linearLayout = byView(R.id.drawer_view);
    }

    //加载数据
    @Override
    protected void initDatas() {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(linearLayout);
            }
        });
        int statusHeight = 0;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) frameLayout.getLayoutParams();
        params.setMargins(0, statusHeight, 0, 0);
        frameLayout.setLayoutParams(params);


    }
}
