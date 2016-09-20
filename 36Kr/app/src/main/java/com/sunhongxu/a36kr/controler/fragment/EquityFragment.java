package com.sunhongxu.a36kr.controler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.SearchActivity;
import com.sunhongxu.a36kr.controler.adapter.EquityFragmentAdapter;
import com.sunhongxu.a36kr.controler.fragment.equity.EquityAllFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 */
public class EquityFragment extends AbsBaseFragment implements View.OnClickListener {

    private TabLayout tabLayout;//定义TanLayout
    private ViewPager viewPager;//定义ViewPager
    private EquityFragmentAdapter adapter;//定义股权投资主界面的Fragment
    private ImageView titleNavigation, titlesActivity;//定义标题栏的活动图片
    private LinearLayout titles;//定义标题栏布局
    private TextView titleTv;//定义标题栏文字
    private ImageView searchImg;//定义标题栏搜索图片
    private boolean isSelete = false;//为活动图片按钮定义选择状态
    private PopupWindow pw;//定义PopupWindow

    //Fragment的复用
    public static EquityFragment newInstance() {
        EquityFragment fragment = new EquityFragment();
        return fragment;
    }

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_equity;
    }

    //初始化组件
    @Override
    protected void initView() {
        tabLayout = byView(R.id.equit_tab);
        viewPager = byView(R.id.equit_vp);
        titleNavigation = byView(R.id.title_img_navigation);
        titlesActivity = byView(R.id.title_activity);
        titles = byView(R.id.root_title);
        titleNavigation.setVisibility(View.INVISIBLE);
        titleTv = byView(R.id.title_tv);
        searchImg = byView(R.id.title_search);
        titleTv.setText("股权投资");
    }

    //加载数据
    @Override
    protected void initDatas() {
        //定义一个装Fragment的数据
        List<Fragment> datas = new ArrayList<>();
        //用Fragment的复用传过去对应的网址
        datas.add(EquityAllFragment.newInstance("all"));
        datas.add(EquityAllFragment.newInstance("underway"));
        datas.add(EquityAllFragment.newInstance("raise"));
        datas.add(EquityAllFragment.newInstance("finish"));
        //初始化适配器
        adapter = new EquityFragmentAdapter(getChildFragmentManager(), datas);
        //绑定适配器，tablayout与ViewPager联动
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //设置标题的内边距，上端是电量栏的距离
        titles.setPadding(0, MarginTop(), 0, 0);
        //设置监听
        titlesActivity.setOnClickListener(this);
        searchImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_activity:
                //当状态为false时，弹出PopWindow，当转太为true时，图片换为X号图片
                if (!isSelete) {
                    //初始化Pop并加载自定义布局
                    pw = new PopupWindow();
                    pw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                    pw.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                    View view = LayoutInflater.from(context).inflate(R.layout.item_titles_aytimg, null);
                    pw.setContentView(view);
                    //设置Pop位置为中间
                    pw.showAtLocation(view, Gravity.CENTER, 0, 0);
                    //设置图片为礼物的图片
                    titlesActivity.setImageResource(R.mipmap.common_bounced_icon_error);
                    isSelete = true;
                } else {
                    //关闭Pop
                    pw.dismiss();
                    //换图片，将状态设为false并且加动画
                    titlesActivity.setImageResource(R.mipmap.nav_icon_activity);
                    isSelete = false;
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.titleaty_rotate);
                    titlesActivity.startAnimation(animation);
                }
                break;
            case R.id.title_search:
                //跳转到搜索界面
                goTo(SearchActivity.class);
                break;
        }
    }
}
