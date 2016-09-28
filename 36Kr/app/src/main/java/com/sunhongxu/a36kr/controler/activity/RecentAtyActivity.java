package com.sunhongxu.a36kr.controler.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.RecentAtyAdapter;
import com.sunhongxu.a36kr.controler.fragment.RecentAtyFragment;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;
import com.sunhongxu.a36kr.view.RefreshLayout;

import java.util.List;

/**
 * 近期活动界面
 */

public class RecentAtyActivity extends AbsBaseActivity implements View.OnClickListener {
    private LinearLayout rootTitle;//定义标题栏
    private ImageView backImg;//定义标题栏返回按钮
    private TextView titleTv;//设置标题
    private FrameLayout frameLayout;
    private LinearLayout recentType;
    private LinearLayout recentTime;


    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_recent_aty;
    }

    //初始化组件
    @Override
    protected void initView() {
        backImg = byView(R.id.find_people_back_img);
        TextView imageViewTitle = byView(R.id.visibity);
        //将占位的隐藏
        imageViewTitle.setVisibility(View.INVISIBLE);
        rootTitle = byView(R.id.recent_aty_root);
        titleTv = byView(R.id.title_find_tv);
        frameLayout = byView(R.id.recent_aty_framelayout);
        recentType = byView(R.id.recent_type);
        recentTime = byView(R.id.recent_time);
        setListener();
    }

    private void setListener() {
        recentTime.setOnClickListener(this);
        recentType.setOnClickListener(this);
        backImg.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        titleTv.setText("近期活动");
        //设置高度
        rootTitle.setPadding(0, MarginTop(), 0, 0);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance(""));
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_people_back_img:
                //结束本界面
                finish();
                break;
            case R.id.recent_time:
                Log.d("RecentAtyActivity", "执行了");
                PopupWindow popTime = new PopupWindow();
                View view = getLayoutInflater().inflate(R.layout.item_pop_time,null);
                popTime.setContentView(view);
                popTime.showAtLocation(view, Gravity.NO_GRAVITY, WindowManager.LayoutParams.MATCH_PARENT,recentTime.getHeight());
                break;
            case R.id.recent_type:
                PopupWindow popType = new PopupWindow();
                View viewType = getLayoutInflater().inflate(R.layout.item_pop_time,null);
                popType.setContentView(viewType);
                popType.showAtLocation(viewType, Gravity.NO_GRAVITY, WindowManager.LayoutParams.MATCH_PARENT,recentTime.getHeight());
                break;
        }
    }
}
