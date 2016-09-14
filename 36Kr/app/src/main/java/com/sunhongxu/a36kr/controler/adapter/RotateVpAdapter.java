package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.DiscoverRotateBean;
import com.sunhongxu.a36kr.model.bean.RotateNewsBean;

import java.util.List;

/**
 * Created by dllo on 16/9/14.
 * 新闻界面轮播图适配器
 */
public class RotateVpAdapter extends PagerAdapter {
    private List<RotateNewsBean.DataBean.PicsBean> datas;
    private Context context;

    public RotateVpAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<RotateNewsBean.DataBean.PicsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newDatas = position % datas.size();
        View view = LayoutInflater.from(context).inflate(R.layout.item_rotate, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.item_rotate_img);
        RotateNewsBean.DataBean.PicsBean picsBeen = datas.get(newDatas);
        Glide.with(context).load(picsBeen.getImgUrl()).into(imageView);
        container.addView(view);
        return view;
    }

}
