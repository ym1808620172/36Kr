package com.sunhongxu.a36kr.controler.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.SetActivity;

/**
 * Created by dllo on 16/9/8.
 * 我的界面
 */
public class MineFragment extends AbsBaseFragment implements View.OnClickListener {

    private ImageView setImg;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private LinearLayout linearLayout;

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        linearLayout = byView(R.id.mine_title);
        setImg = byView(R.id.mine_set_img);
    }

    @Override
    protected void initDatas() {
        linearLayout.setPadding(50, MarginTop() + 50, 50, 50);
        setImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_set_img:
                goTo(SetActivity.class);
                break;
        }
    }
}
