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

import java.util.List;

/**
 * Created by dllo on 16/9/20.
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recent_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RecentAtyBean.DataBean.DataBeans dataBeans = dataBeanses.get(position);
        if (dataBeans != null) {
            viewHolder.recentContentTv.setText(dataBeans.getActivityDesc());
            viewHolder.recentTvTitle.setText(dataBeans.getActivityName());
            viewHolder.recentWhere.setText(dataBeans.getActivityCity());
            viewHolder.recentTime.setText(dataBeans.getActivityTime());
            Picasso.with(context).load(dataBeans.getActivityImg()).into(viewHolder.recentImgBig);
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

        private final ImageView recentImgBig;
        private final TextView recentTvTitle;
        private final TextView recentContentTv;
        private final TextView recentNowTv;
        private final TextView recentWhere;
        private final TextView recentTime;

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
