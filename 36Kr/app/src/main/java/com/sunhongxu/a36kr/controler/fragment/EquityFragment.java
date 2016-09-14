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
import com.sunhongxu.a36kr.utils.ScreenSizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/8.
 */
public class EquityFragment extends AbsBaseFragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EquityFragmentAdapter adapter;
    private ImageView titleNavigation, titlesActivity;
    private LinearLayout titles;
    private TextView titleTv;
    private ImageView searchImg;
    private boolean isSelete = false;
    private PopupWindow pw;

    public static EquityFragment newInstance() {

        Bundle args = new Bundle();

        EquityFragment fragment = new EquityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_equity;
    }

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

    @Override
    protected void initDatas() {
        List<Fragment> datas = new ArrayList<>();
        datas.add(EquityAllFragment.newInstance("all"));
        datas.add(EquityAllFragment.newInstance("underway"));
        datas.add(EquityAllFragment.newInstance("raise"));
        datas.add(EquityAllFragment.newInstance("finish"));
        adapter = new EquityFragmentAdapter(getChildFragmentManager(), datas);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        titles.setPadding(0, MarginTop(), 0, 0);
        titlesActivity.setOnClickListener(this);
        searchImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_activity:
                if (!isSelete) {
                    pw = new PopupWindow();
                    pw.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                    pw.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                    View view = LayoutInflater.from(context).inflate(R.layout.item_titles_aytimg, null);
                    pw.setContentView(view);
                    pw.showAtLocation(view, Gravity.CENTER, 0, 0);
                    titlesActivity.setImageResource(R.mipmap.common_bounced_icon_error);
                    isSelete = true;
                } else {
                    pw.dismiss();
                    titlesActivity.setImageResource(R.mipmap.nav_icon_activity);
                    isSelete = false;
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.titleaty_rotate);
                    titlesActivity.startAnimation(animation);
                }
                break;
            case R.id.title_search:
                Intent intent = new Intent(context, SearchActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}
