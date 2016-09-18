package com.sunhongxu.a36kr.controler.activity;


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
import java.util.Map;

public class SearchActivity extends AbsBaseActivity implements View.OnClickListener {


    private LinearLayout linearLayout;
    private ImageView searchImg;
    private TextView searchTv;
    private EditText searchEt;
    private SearchAdapter searchAdapter;
    private LinearLayout listViewLl;
    private ListView listView;
    private TextView historyTv;
    private LinearLayout searchLl;
    private boolean isHave = false;

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
        searchLl = byView(R.id.search_no_ll);

    }

    @Override
    protected void initDatas() {
        linearLayout.setPadding(0, MarginTop(), 0, 0);
        SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
        isHave = preferences.getBoolean("ishave", false);
        if (isHave) {
            searchAdapter = new SearchAdapter(this);
            listView.setAdapter(searchAdapter);
            Map<String, ?> a = preferences.getAll();
            List<String> mapKeyList = new ArrayList(a.keySet());
            List<SearchBean> datas = new ArrayList<>();
            for (int i = 0; i < mapKeyList.size(); i++) {
                mapKeyList.remove("ishave");
                SearchBean sb = new SearchBean();
                sb.setContent(mapKeyList.get(i));
                datas.add(sb);
            }
            searchAdapter.setDatas(datas);
            searchLl.setVisibility(View.GONE);
        }
        //添加清理文字的尾布局,并设监听
        addFooter();
    }

    private void addFooter() {
        View view = getLayoutInflater().inflate(R.layout.item_fooder_search, null);
        TextView searchClean = (TextView) view.findViewById(R.id.search_clear);
        searchClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                searchLl.setVisibility(View.VISIBLE);
            }
        });
        listView.addFooterView(view);
    }


    @Override
    public void onClick(View v) {
        String contentEt = searchEt.getText().toString();
        switch (v.getId()) {
            case R.id.search_aty_img:
                if (contentEt.isEmpty()) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(contentEt, contentEt);
                    editor.putBoolean("ishave", true);
                    editor.commit();
                }

                break;
            case R.id.search_aty_tv_cancel:
                finish();
                break;

        }
    }
}
