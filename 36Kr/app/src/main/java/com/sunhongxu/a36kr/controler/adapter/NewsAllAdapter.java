package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * Created by dllo on 16/9/13.
 * 新闻界面的新闻Fragment的适配器
 */
public class NewsAllAdapter extends BaseAdapter {
    private Context context;
    private List<NewsAllBean.DataBean.DataBeans> datas;
    private String string;

    //构造方法,将不同的网址传入设配器
    public NewsAllAdapter(Context context, String string) {
        this.context = context;
        this.string = string;
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
            int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
            //获得时间并转型
            long time = bean.getPublishTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date nowTime = new Date(System.currentTimeMillis());
            //获得当前时间
            long timeLong = nowTime.getTime();
            long minTime = timeLong - time;
            //根据时间差判断显示的时间是什么....
            if (minTime < 3600000) {
                if (minTime / 60000 > 0) {
                    viewHoler.time.setText(minTime / 60000 + "分钟前");
                } else {
                    viewHoler.time.setText(minTime /1000 + "秒前");
                }
            } else if ((3600000 * 48) < minTime && minTime < (3600000 * 72)) {
                viewHoler.time.setText("前天");
            } else if ((3600000 * 24) < minTime && minTime < (3600000 * 48)) {
                viewHoler.time.setText("昨天");
            } else if (3600000 < minTime && minTime < (3600000 * 24)) {
                Date date = new Date(time);
                String fromatTimt = sdf.format(date);
                viewHoler.time.setText(fromatTimt);
            } else {
                SimpleDateFormat sdfOther = new SimpleDateFormat("MM月dd号");
                Date otherData = new Date(time);
                String OtherTime = sdfOther.format(otherData);
                viewHoler.time.setText(OtherTime);
            }

            //设置时间
            //设置作者名字
            viewHoler.author.setText(bean.getUser().getName());
            //设置标题
            viewHoler.title.setText(bean.getTitle());
            //根据网址的不同,判断是否需要显示新闻类型
            if (string != null) {
                if (string.equals("all")) {
                    viewHoler.column.setVisibility(View.VISIBLE);
                } else {
                    viewHoler.column.setVisibility(View.INVISIBLE);
                }
            }
            //获得根据ColumnId判断文字的颜色
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
                case "102":
                    ColorStateList cslCool = resource.getColorStateList(R.color.columbig);
                    viewHoler.column.setTextColor(cslCool);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                case "103":
                    ColorStateList cslFriend = resource.getColorStateList(R.color.columbig);
                    viewHoler.column.setTextColor(cslFriend);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
                default:
                    ColorStateList cslDefault = resource.getColorStateList(R.color.columbig);
                    viewHoler.column.setTextColor(cslDefault);
                    viewHoler.column.setText(bean.getColumnName());
                    break;
            }
            //设置新闻的图片
            Picasso.with(context).load(bean.getFeatureImg()).resize(height / 6, height / 8).into(viewHoler.imageView);
        }
        return convertView;
    }


    public class ViewHoler {

        private ImageView imageView;//定义新闻的图片
        private TextView author;//定义新闻的作者
        private TextView column;//定义新闻的内容
        private TextView title;//定义新闻的标题
        private TextView time;//定义新闻的时间

        public ViewHoler(View view) {
            imageView = (ImageView) view.findViewById(R.id.news_all_list_img);
            author = (TextView) view.findViewById(R.id.news_all_list_author);
            column = (TextView) view.findViewById(R.id.news_all_list_column);
            title = (TextView) view.findViewById(R.id.news_all_list_title);
            time = (TextView) view.findViewById(R.id.news_all_list_time);
        }
    }
}
