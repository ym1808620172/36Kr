package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.FindPeopleBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/9/18.
 * 寻找投资人的适配器
 */
public class FindPeopleAdapter extends BaseAdapter {
    List<FindPeopleBean.DataBean.DataBeans> dataBeanses = new ArrayList<>();
    private Context context;

    public FindPeopleAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanses(List<FindPeopleBean.DataBean.DataBeans> dataBeanses) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_find_person_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FindPeopleBean.DataBean.DataBeans dataBeans = dataBeanses.get(position);
        int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
        int weight = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
        if (dataBeans != null) {
            //设置名字
            viewHolder.nameTv.setText(dataBeans.getUser().getName());
            //如果领域的数组长度为0的时候,设置Tv为:未披露
            if (dataBeans.getFocusIndustry().size() == 0) {
                viewHolder.domainTv.setText("未披露");
            } else {
                //不为空的时候,先定义的一空格字符串
                String focusIndustry = " ";
                for (int i = 0; i < dataBeans.getFocusIndustry().size(); i++) {
                    //根据数组长度获得数组里的内容
                    String datas = dataBeans.getFocusIndustry().get(i);
                    //用数组里的内容挨个拼接字符串
                    focusIndustry = datas + " "+ focusIndustry ;
                }
                //设置领域Tv的内容
                viewHolder.domainTv.setText(focusIndustry);
            }
            //同样,阶段数据长度为0的时候,设置Tv为:未披露
            if (dataBeans.getInvestPhases().size() == 0) {
                viewHolder.phaseTv.setText("未披露");
            } else {
                //根据数组长度获得数组里的内容
                String investPhases = "";
                for (int i = 0; i < dataBeans.getInvestPhases().size(); i++) {
                    String datas = dataBeans.getInvestPhases().get(i);
                    investPhases = datas + " "+ investPhases ;
                }
                viewHolder.phaseTv.setText(investPhases);
            }
            //获得头像的Url,不为空的时候设置头像
            if (dataBeans.getUser().getAvatar() != null) {
                Picasso.with(context).load(dataBeans.getUser().getAvatar()).resize(weight / 10, height / 10).into(viewHolder.img);
            } else {
                //如果为空的时候设置这个头像0.0可以点击去看看
                viewHolder.img.setImageResource(R.mipmap.bj);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        private final TextView nameTv;//定义寻找投资人名字
        private final TextView domainTv;//定义寻找投资人的领域
        private final TextView phaseTv;//定义寻找投资人的阶段
        private final ImageView img;//定义寻找投资人头像

        public ViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_find_people_name);
            domainTv = (TextView) view.findViewById(R.id.item_find_people_domain);
            phaseTv = (TextView) view.findViewById(R.id.item_find_people_phase);
            img = (ImageView) view.findViewById(R.id.item_find_people_img);
        }
    }
}
