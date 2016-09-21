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
        Log.d("aaaa", "convertView:" );
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
            viewHolder.nameTv.setText(dataBeans.getUser().getName());
            if (dataBeans.getFocusIndustry().size() == 0) {
                viewHolder.domainTv.setText("未披露");
            } else {
                String focusIndustry = " ";
                for (int i = 0; i < dataBeans.getFocusIndustry().size(); i++) {
                    String datas = dataBeans.getFocusIndustry().get(i);
                    focusIndustry = datas + " "+ focusIndustry ;
                }
                viewHolder.domainTv.setText(focusIndustry);
            }
            if (dataBeans.getInvestPhases().size() == 0) {
                viewHolder.phaseTv.setText("未披露");
            } else {
                String investPhases = "";
                for (int i = 0; i < dataBeans.getInvestPhases().size(); i++) {
                    String datas = dataBeans.getInvestPhases().get(i);
                    investPhases = datas + " "+ investPhases ;
                }
                viewHolder.phaseTv.setText(investPhases);
            }
            if (dataBeans.getUser().getAvatar() != null) {
                Picasso.with(context).load(dataBeans.getUser().getAvatar()).resize(weight / 10, height / 10).into(viewHolder.img);
            } else {
                viewHolder.img.setImageResource(R.mipmap.bj);
            }
        }
        return convertView;
    }

    public class ViewHolder {
        private final TextView nameTv;
        private final TextView domainTv;
        private final TextView phaseTv;
        private final ImageView img;

        public ViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_find_people_name);
            domainTv = (TextView) view.findViewById(R.id.item_find_people_domain);
            phaseTv = (TextView) view.findViewById(R.id.item_find_people_phase);
            img = (ImageView) view.findViewById(R.id.item_find_people_img);
        }
    }
}
