package com.sunhongxu.a36kr.controler.fragment.news;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;

/**
 * Created by dllo on 16/9/10.
 */
public class NewsAllFragment extends AbsBaseFragment {

    private TextView textView;

    public static NewsAllFragment newInstance(String str) {

        Bundle args = new Bundle();

        NewsAllFragment fragment = new NewsAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView() {
        textView = byView(R.id.news_content_tv);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getArguments();
        String string = String.valueOf(bundle);
        Log.d("NewsAllFragment", string);
    }
}
