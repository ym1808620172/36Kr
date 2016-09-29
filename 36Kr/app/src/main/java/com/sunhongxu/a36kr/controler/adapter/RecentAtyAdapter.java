package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.RecentAtyBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;

import java.util.List;

/**
 * Created by dllo on 16/9/20.
 * 近期活动的适配器
 */
public class RecentAtyAdapter extends BaseAdapter {
    private Context context;
    private List<RecentAtyBean.DataBean.DataBeans> dataBeanses;

    public RecentAtyAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanses(List<RecentAtyBean.DataBean.DataBeans> dataBeanses) {
        this.dataBeanses = dataBeanses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataBeanses != null && dataBeanses.size() > 0 ? dataBeanses.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dataBeanses != null && dataBeanses.size() > 0 ? dataBeanses.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        int width = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
        int hegiht = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recent_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RecentAtyBean.DataBean.DataBeans dataBeans = dataBeanses.get(position);
        if (dataBeans != null) {
            //设置新闻内容
            viewHolder.recentContentTv.setText(dataBeans.getActivityDesc());
            //设置新闻标题
            viewHolder.recentTvTitle.setText(dataBeans.getActivityName());
            //设置新闻地点
            viewHolder.recentWhere.setText(dataBeans.getActivityCity());
            //设置新闻时间
            viewHolder.recentTime.setText(dataBeans.getActivityTime());
            //设置图片
            Picasso.with(context).load(dataBeans.getActivityImg()).resize(width,hegiht/3).into(viewHolder.recentImgBig);
            //根据Status设置背景颜色
            String Status = dataBeans.getActivityStatus();
            switch (Status) {
                case "报名中":
                    viewHolder.recentNowTv.setBackgroundColor(Color.BLUE);
                    viewHolder.recentNowTv.setText(Status);
                    break;
                case "活动中":
                    viewHolder.recentNowTv.setBackgroundColor(Color.YELLOW);
                    viewHolder.recentNowTv.setText(Status);
                    break;
                case "已结束":
                    viewHolder.recentNowTv.setBackgroundColor(Color.GRAY);
                    viewHolder.recentNowTv.setText(Status);
                    break;
            }
        }
        return convertView;
    }

    class ViewHolder {

        private final ImageView recentImgBig;//定义图片
        private final TextView recentTvTitle;//定义活动标题
        private final TextView recentContentTv;//定义活动内容
        private final TextView recentNowTv;//定义是是否还在包名的Tv
        private final TextView recentWhere;//定义活动位置的Tv
        private final TextView recentTime;//定义活动时间的Tv

        public ViewHolder(View view) {
            recentImgBig = (ImageView) view.findViewById(R.id.item_recent_img_big);
            recentTvTitle = (TextView) view.findViewById(R.id.item_recent_tv_title);
            recentContentTv = (TextView) view.findViewById(R.id.item_recent_tv_content);
            recentNowTv = (TextView) view.findViewById(R.id.item_recent_tv_now);
            recentWhere = (TextView) view.findViewById(R.id.item_recent_tv_where);
            recentTime = (TextView) view.findViewById(R.id.item_recent_tv_time);
        }
    }
}
