package com.sunhongxu.a36kr.controler.fragment.equity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.MainActivity;
import com.sunhongxu.a36kr.controler.adapter.EquiteNewsAdapter;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.EquityNetConstants;
import com.sunhongxu.a36kr.view.ReFlashListView;

import java.util.List;

/**
 * Created by dllo on 16/9/10.
 * 全部Fragment
 */
public class EquityAllFragment extends AbsBaseFragment implements VolleyRequest {

    private ReFlashListView listView;
    private EquiteNewsAdapter adapter;

    public static EquityAllFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putSerializable("URL", url);
        EquityAllFragment fragment = new EquityAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_quity_all;
    }

    @Override
    protected void initView() {
        listView = byView(R.id.equity_list_view);

    }

    @Override
    protected void initDatas() {
        adapter = new EquiteNewsAdapter(context);
        listView.setAdapter(adapter);
        Bundle bundle = getArguments();
        String string = bundle.getString("URL");

        VolleyInstance.getInstance().startInstance(EquityNetConstants.EQUITYHELPER + string + EquityNetConstants.EQUITYHELPEREND, this);
    }


    @Override
    public void success(String result) {
        Gson gson = new Gson();
        EquityBean bean = gson.fromJson(result, EquityBean.class);
        List<EquityBean.DataBean.DataBeans> datas = bean.getData().getData();
        adapter.setDatas(datas);
    }

    @Override
    public void failure() {

    }
    @Override
    public void onReflash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                }
                // 通知界面显示
                listView.setInterface(MainActivity.this);
                listView.setAdapter(adapter);
                // 通知ListView刷新数据完毕
                listView.reflshComplete();
            }
        },2000);
        // 获取最新数据

    }
}
