package com.sunhongxu.a36kr.controler.fragment;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.activity.VideoActivity;
import com.sunhongxu.a36kr.controler.adapter.VideoAdapter;
import com.sunhongxu.a36kr.model.bean.VideoBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.VideoRecyclerView;

import java.util.List;

/**
 * Created by dllo on 16/9/26.
 */
public class VideoFragment extends AbsBaseFragment implements VolleyRequest, VideoRecyclerView {

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private LinearLayout videoRoot;

    public static VideoFragment newInstance(String URL) {
        Bundle args = new Bundle();
        args.putString("URL", URL);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayout() {
        getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        videoRoot = byView(R.id.video_root);
        recyclerView = byView(R.id.video_recycler);
    }

    @Override
    protected void initDatas() {
        Bundle bundle = getArguments();
        String URL = bundle.getString("URL");
        VolleyInstance.getInstance().startInstance(URL, this);
        adapter = new VideoAdapter(context);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context, 1, false);
        recyclerView.setLayoutManager(manager);
        adapter.setRecyclerView(this);
        videoRoot.setPadding(0, MarginTop(), 0, 0);

    }

    @Override
    public void success(String result) {
        Log.d("aaaa", result + "xxx");
        Gson gson = new Gson();
        VideoBean videoBean = gson.fromJson(result, VideoBean.class);
        List<VideoBean.DataBean.DataBeans> dataBeanses = videoBean.getData().getDatas();
        adapter.setDataBeanses(dataBeanses);
    }

    @Override
    public void failure() {

    }

    @Override
    public void onClickVideoRecyclerView(int position, VideoBean.DataBean.DataBeans bean) {
        Bundle bundle = new Bundle();
        bundle.putString("URL",bean.getGroup().getMp4_url());
        bundle.putString("titile",bean.getGroup().getTitle());
        goTo(VideoActivity.class,bundle);
    }
}
