package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.utils.ScreenSizeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dllo on 16/9/13.
 */
public class NewsAllAdapter extends BaseAdapter {
    private Context context;
    private List<NewsAllBean.DataBean.DataBeans> datas;

    public NewsAllAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<NewsAllBean.DataBean.DataBeans> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas != null && datas.size() > 0 ? datas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return datas != null && datas.size() > 0 ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_news_all_list, parent, false);
            viewHoler = new ViewHoler(convertView);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }
        NewsAllBean.DataBean.DataBeans bean = datas.get(position);
        if (bean != null) {
            int height = ScreenSizeUtils.getScreenSize(context, ScreenSizeUtils.ScreenState.HEIGHT);
            long time = bean.getPublishTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = new Date(time);
            String fromatTimt = sdf.format(date);
            viewHoler.time.setText(fromatTimt);
            viewHoler.author.setText(bean.getUser().getName());
            viewHoler.title.setText(bean.getTitle());
            String column = bean.getColumnId();
            Resources resource = context.getResources();
            switch (column) {
                case "67":
                    ColorStateList cslEarly = resource.getColorStateList(R.color.columnearly);
                    viewHoler.column.setTextColor(cslEarly);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "70":
                    ColorStateList cslDepth = resource.getColorStateList(R.color.columdepth);
                    viewHoler.column.setTextColor(cslDepth);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "69":
                    ColorStateList csl = resource.getColorStateList(R.color.columnearly);
                    viewHoler.column.setTextColor(csl);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "23":
                    ColorStateList cslBig = resource.getColorStateList(R.color.columbig);
                    viewHoler.column.setTextColor(cslBig);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "72":
                    ColorStateList cslOther = resource.getColorStateList(R.color.columbig);
                    viewHoler.column.setTextColor(cslOther);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "68":
                    ColorStateList cslB = resource.getColorStateList(R.color.columbend);
                    viewHoler.column.setTextColor(cslB);
                    viewHoler.column.setText(bean.getColumnName());
                    break;

                case "25":
                    ColorStateList cslStudy = resource.getColorStateList(R.color.columnearly);
                    viewHoler.column.setTextColor(cslStudy);
                    viewHoler.column.setText(bean.getColumnName());
                    break;

            }

            Picasso.with(context).load(bean.getFeatureImg()).resize(height / 6, height / 8).into(viewHoler.imageView);

        }
        return convertView;
    }

    class ViewHoler {

        private ImageView imageView;
        private TextView author;
        private TextView column;
        private TextView title;
        private TextView time;

        public ViewHoler(View view) {
            imageView = (ImageView) view.findViewById(R.id.news_all_list_img);
            author = (TextView) view.findViewById(R.id.news_all_list_author);
            column = (TextView) view.findViewById(R.id.news_all_list_column);
            title = (TextView) view.findViewById(R.id.news_all_list_title);
            time = (TextView) view.findViewById(R.id.news_all_list_time);
        }
    }
}
