package com.sunhongxu.a36kr.controler.activity;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.utils.DataCleanManager;

/**
 * 设置界面
 */

public class SetActivity extends AbsBaseActivity implements View.OnClickListener {

    private LinearLayout setRootLl;
    private ImageView backImg;
    private TextView titleTv;
    private TextView cleanTv;
    private TextView dataTv;

    //加载布局
    @Override
    protected int setLayout() {
        return R.layout.activity_set;
    }

    //初始化组件
    @Override
    protected void initView() {
        setRootLl = byView(R.id.set_root);
        backImg = byView(R.id.find_people_back_img);
        TextView imageViewTitle = byView(R.id.visibity);
        imageViewTitle.setVisibility(View.INVISIBLE);
        titleTv = byView(R.id.title_find_tv);
        backImg.setOnClickListener(this);
        cleanTv = byView(R.id.set_data_clean_tv);
        dataTv = byView(R.id.set_date_tv);
        cleanTv.setOnClickListener(this);
    }

    //加载数据
    @Override
    protected void initDatas() {
        setRootLl.setPadding(0, MarginTop(), 0, 0);
        titleTv.setText("设置");
        try {
            dataTv.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_people_back_img:
                finish();
                break;
            case R.id.set_data_clean_tv:
                DataCleanManager.clearAllCache(this);
                try {
                    dataTv.setText(DataCleanManager.getTotalCacheSize(this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
