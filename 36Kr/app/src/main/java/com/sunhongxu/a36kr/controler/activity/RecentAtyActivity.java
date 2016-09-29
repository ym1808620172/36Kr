package com.sunhongxu.a36kr.controler.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    private LinearLayout rootLl;//定义标题栏
    private ImageView backImg;//定义标题栏返回按钮
    private TextView titleTv;//设置标题
    private FrameLayout frameLayout;
    private LinearLayout recentType;
    private LinearLayout recentTime;
    private LinearLayout popRootLl;
    private PopupWindow popTime;
    private PopupWindow popType;
    private LinearLayout rootTitle;
    private boolean popTimeOpen = false;
    private boolean popTypeOpen = false;
    private TextView typeTv;

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
        rootLl = byView(R.id.recent_aty_root);
        titleTv = byView(R.id.title_find_tv);
        frameLayout = byView(R.id.recent_aty_framelayout);
        recentType = byView(R.id.recent_type);
        recentTime = byView(R.id.recent_time);
        popRootLl = byView(R.id.recent_pop_ll);
        rootTitle = byView(R.id.find_title_ll);
        typeTv = byView(R.id.type_tv);
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
        rootLl.setPadding(0, MarginTop(), 0, 0);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance(""));
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.find_people_back_img:
                //结束本界面
                finish();
                break;
            case R.id.recent_time:
                setTimePop();
                break;
            case R.id.recent_type:
                setTypePop();
                break;
            case R.id.pop_type_all:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("全部");
                titleTv.setText("全部");
                break;
            case R.id.pop_type_day:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("1" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("Demo Day");
                titleTv.setText("Demo Day");
                break;
            case R.id.pop_type_equity:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("4" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("股权投资");
                titleTv.setText("股权投资");
                break;
            case R.id.pop_type_quity:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("5" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("极速融资");
                titleTv.setText("极速融资");
                break;
            case R.id.pop_type_service:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("6" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("企业服务");
                titleTv.setText("企业服务");
                break;
            case R.id.pop_type_space:
                transaction.replace(R.id.recent_aty_framelayout, RecentAtyFragment.newInstance("7" + NetConstants.RECENTATYEND));
                if (popType != null && popType.isShowing()) {
                    popType.dismiss();
                    popTypeOpen = false;
                }
                typeTv.setText("氪空间");
                titleTv.setText("氪空间");
                break;
        }
        transaction.commit();

    }

    private void setTypePop() {
        if (!popTypeOpen) {
            popType = new PopupWindow();
            popType.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            popType.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            View viewType = getLayoutInflater().inflate(R.layout.item_pop_type, null);
            TextView typeAll = (TextView) viewType.findViewById(R.id.pop_type_all);
            TextView typeDay = (TextView) viewType.findViewById(R.id.pop_type_day);
            TextView typeEquity = (TextView) viewType.findViewById(R.id.pop_type_equity);
            TextView typeQuity = (TextView) viewType.findViewById(R.id.pop_type_quity);
            TextView typeService = (TextView) viewType.findViewById(R.id.pop_type_service);
            TextView typeSpace = (TextView) viewType.findViewById(R.id.pop_type_space);
            typeAll.setOnClickListener(this);
            typeDay.setOnClickListener(this);
            typeEquity.setOnClickListener(this);
            typeQuity.setOnClickListener(this);
            typeService.setOnClickListener(this);
            typeSpace.setOnClickListener(this);
            popType.setContentView(viewType);
            popType.showAtLocation(viewType, Gravity.NO_GRAVITY, 0, MarginTop() + popRootLl.getHeight() + rootTitle.getHeight());
            popTypeOpen = true;
        } else {
            popTypeOpen = false;
            popType.dismiss();
        }
        if (popTime != null && popTime.isShowing()) {
            popTime.dismiss();
            popTimeOpen = false;
        }
    }

    private void setTimePop() {
        if (!popTimeOpen) {
            popTime = new PopupWindow();
            popTime.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            popTime.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            View view = getLayoutInflater().inflate(R.layout.item_pop_time, null);
            popTime.setContentView(view);
            popTime.showAtLocation(view, Gravity.NO_GRAVITY, 0, MarginTop() + popRootLl.getHeight() + rootTitle.getHeight());
            popTimeOpen = true;
        } else {
            popTime.dismiss();
            popTimeOpen = false;
        }
        if (popType != null && popType.isShowing()) {
            popType.dismiss();
            popTypeOpen = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (popTime != null && popTime.isShowing()) {
            popTime.dismiss();
        }
        if (popType != null && popType.isShowing()) {
            popType.dismiss();
        }
    }
}
