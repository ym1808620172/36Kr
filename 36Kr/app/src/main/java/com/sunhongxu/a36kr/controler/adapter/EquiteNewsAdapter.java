package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.utils.ScreenSizeUtils;

import java.util.List;

/**
 * Created by dllo on 16/9/13.
 */
public class EquiteNewsAdapter extends BaseAdapter {
    private Context context;
    private List<EquityBean.DataBean.DataBeans> datas;

    public EquiteNewsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<EquityBean.DataBean.DataBeans> datas) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_equity_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EquityBean.DataBean.DataBeans dataBeans = datas.get(position);
        if (dataBeans != null) {
            int height = ScreenSizeUtils.getScreenSize(context, ScreenSizeUtils.ScreenState.HEIGHT);
            int weight = ScreenSizeUtils.getScreenSize(context, ScreenSizeUtils.ScreenState.WIDTH);
            Picasso.with(context).load(dataBeans.getCompany_logo()).resize(height / 10, height / 10).into(viewHolder.companyLogo);
            Picasso.with(context).load(dataBeans.getFile_list_img()).resize(weight, height / 3).into(viewHolder.filelistImg);
            viewHolder.adcontentPeople.setText(dataBeans.getCf_advantage().get(position).getAdcontent());
            viewHolder.leadName.setText(dataBeans.getLead_name());
            viewHolder.companyName.setText(dataBeans.getCompany_name());
            viewHolder.companyBrief.setText(dataBeans.getCompany_brief());
            viewHolder.adcontentTools.setText(dataBeans.getCf_advantage().get(position).getAdname());
        }
        return convertView;
    }

    class ViewHolder {

        private final ImageView companyLogo;
        private final TextView companyName;
        private final TextView companyBrief;
        private final ImageView filelistImg;
        private final TextView leadName;
        private final TextView adcontentPeople;
        private final TextView adcontentTools;
        private final TextView desc;
        private final TextView rate;
        private final SeekBar seekBar;

        public ViewHolder(View view) {
            companyLogo = (ImageView) view.findViewById(R.id.company_logo);
            companyName = (TextView) view.findViewById(R.id.company_name);
            companyBrief = (TextView) view.findViewById(R.id.company_brief);
            filelistImg = (ImageView) view.findViewById(R.id.file_list_img);
            leadName = (TextView) view.findViewById(R.id.lead_name);
            adcontentPeople = (TextView) view.findViewById(R.id.adcontent_people);
            adcontentTools = (TextView) view.findViewById(R.id.adcontent_tools);
            desc = (TextView) view.findViewById(R.id.desc);
            rate = (TextView) view.findViewById(R.id.rate);
            seekBar = (SeekBar) view.findViewById(R.id.seekbar);
        }
    }
}
