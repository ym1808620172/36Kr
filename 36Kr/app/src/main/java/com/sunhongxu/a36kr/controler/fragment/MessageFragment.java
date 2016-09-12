package com.sunhongxu.a36kr.controler.fragment;

import android.os.Bundle;

import com.sunhongxu.a36kr.R;

/**
 * Created by dllo on 16/9/10.
 */
public class MessageFragment extends AbsBaseFragment {
    public static MessageFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }
}
