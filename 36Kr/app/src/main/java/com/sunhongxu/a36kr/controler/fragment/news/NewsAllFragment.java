package com.sunhongxu.a36kr.controler.fragment.news;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.NewsAllAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NewsNetHelper;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 */
public class NewsAllFragment extends AbsBaseFragment implements VolleyRequest {

    private NewsAllAdapter allAdapter;
    private ListView listView;

    public static NewsAllFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putSerializable("url", url);
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
        listView = byView(R.id.news_all_listview);

    }

    @Override
    protected void initDatas() {
        allAdapter = new NewsAllAdapter(context);
        listView.setAdapter(allAdapter);
        Bundle bundle = getArguments();
        String string = bundle.getString("url");
        Log.d("NewsAllFragment", string);
        VolleyInstance.getInstance().startInstance(NewsNetHelper.NEWSHELPER + string + NewsNetHelper.NEWSURLEND, this);
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        NewsAllBean datas = gson.fromJson(result, NewsAllBean.class);
        List<NewsAllBean.DataBean.DataBeans> dataBeanses = datas.getData().getData();
        allAdapter.setDatas(dataBeanses);
    }

    @Override
    public void failure() {

    }
}
