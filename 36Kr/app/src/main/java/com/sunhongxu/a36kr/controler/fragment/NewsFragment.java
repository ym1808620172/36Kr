package com.sunhongxu.a36kr.controler.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.fragment.news.NewsAllFragment;

/**
 * Created by dllo on 16/9/8.
 * 新闻界面的Fragment
 *
 * @author sunhongxu
 */
public class NewsFragment extends AbsBaseFragment {


    //复用Fragment
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_news;
    }

    //初始化控件
    @Override
    protected void initView() {

    }

    //加载数据
    @Override
    protected void initDatas() {
        //设置默认页
        setFragment();
    }

    public void changeFragment(Fragment fragment) {
        //替换界面,数据为从主界面传过来的Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.framelayout_news, fragment).commit();
    }

    private void setFragment() {
        //设置默认页为全部新闻界面
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout_news, NewsAllFragment.newInstance("all"));
        transaction.commit();

    }

}
