package com.sunhongxu.a36kr.controler.activity;


import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.SearchAdapter;
import com.sunhongxu.a36kr.model.bean.SearchBean;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AbsBaseActivity implements View.OnClickListener {


    private LinearLayout linearLayout;
    private ImageView searchImg;
    private TextView searchTv;
    private EditText searchEt;
    private SearchAdapter searchAdapter;
    private List<SearchBean> datas;
    private LinearLayout listViewLl;
    private ListView listView;
    private TextView historyTv;
    private LinearLayout searchLl;
    private TextView clearTv;

    @Override
    protected int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        linearLayout = byView(R.id.search_root_ll);
        searchImg = byView(R.id.search_aty_img);
        searchEt = byView(R.id.search_aty_et);
        searchTv = byView(R.id.search_aty_tv_cancel);
        searchImg.setOnClickListener(this);
        searchTv.setOnClickListener(this);
        listView = byView(R.id.search_listview);
        listViewLl = byView(R.id.search_aty_list_ll);
        historyTv = byView(R.id.history_tv);
//        searchLl = byView(R.id.search_no_ll);
        clearTv = byView(R.id.search_clear);

    }

    @Override
    protected void initDatas() {
        linearLayout.setPadding(0, MarginTop(), 0, 0);

    }


    @Override
    public void onClick(View v) {
        String contentEt = searchEt.getText().toString();
        switch (v.getId()) {
            case R.id.search_aty_img:
                if (contentEt.isEmpty()) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {

                }

                break;
            case R.id.search_aty_tv_cancel:
                finish();
                break;
            case R.id.search_clear:

                break;
        }
    }
}
