package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.EquityBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;

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
            int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
            int weight = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
            Picasso.with(context).load(dataBeans.getCompany_logo()).resize(weight / 10, height / 10).into(viewHolder.companyLogo);//公司图片
            Picasso.with(context).load(dataBeans.getFile_list_img()).into(viewHolder.filelistImg);//文件图片
            viewHolder.adcontentPeople0.setText(dataBeans.getCf_advantage().get(0).getAdcontent());//创始人tag
            viewHolder.adcontentTools0.setText(dataBeans.getCf_advantage().get(0).getAdname());//创始人名字
            viewHolder.adcontentPeople1.setText(dataBeans.getCf_advantage().get(1).getAdname());//孵化器tag
            viewHolder.adcontentTools1.setText(dataBeans.getCf_advantage().get(1).getAdcontent());//孵化器
            viewHolder.leadName.setText(dataBeans.getLead_name());//领资方
            viewHolder.companyName.setText(dataBeans.getCompany_name());//公司名字
            viewHolder.companyBrief.setText(dataBeans.getCompany_brief());//公司类型
            viewHolder.rate.setText("已募资" + (int) (dataBeans.getRate() * 100) + "%");//完成进度
            viewHolder.seekBar.setProgress((int) (dataBeans.getRate() * 100));//进度条
            String descAll = dataBeans.getFundStatus().getDesc();
            //根据类型设置颜色
            if (descAll.equals("募资中")) {
                Resources resource = context.getResources();
                ColorStateList descColor = resource.getColorStateList(R.color.desc_color_ing);
                viewHolder.desc.setTextColor(descColor);//简介
                viewHolder.desc.setText(dataBeans.getFundStatus().getDesc());
                viewHolder.introductionBtn.setText("认购");
                ColorStateList descColorBtn = resource.getColorStateList(R.color.introduction_color_btn_tv);
                viewHolder.introductionBtn.setTextColor(descColorBtn);
                viewHolder.introductionBtn.setBackgroundColor(Color.YELLOW);
            } else {
                Resources resource = context.getResources();
                ColorStateList descColor = resource.getColorStateList(R.color.desc_color_end);
                viewHolder.desc.setTextColor(descColor);//简介
                viewHolder.desc.setText(dataBeans.getFundStatus().getDesc());
                viewHolder.introductionBtn.setText("去看看");
            }
        }
        return convertView;
    }

    class ViewHolder {

        private final ImageView companyLogo;//公司图片
        private final TextView companyName;//公司名字
        private final TextView companyBrief;//公司类型
        private final ImageView filelistImg;//文件图片
        private final TextView leadName;//领资方
        private final TextView adcontentPeople0;//创始人tag
        private final TextView adcontentTools0;//创始人名字
        private final TextView adcontentPeople1;//孵化器tag
        private final TextView adcontentTools1;//孵化器
        private final TextView desc;//简介
        private final TextView rate;//完成百分比
        private final SeekBar seekBar;//进度条
        private final Button introductionBtn;//认购按钮

        public ViewHolder(View view) {

            companyLogo = (ImageView) view.findViewById(R.id.company_logo);
            companyName = (TextView) view.findViewById(R.id.company_name);
            companyBrief = (TextView) view.findViewById(R.id.company_brief);
            filelistImg = (ImageView) view.findViewById(R.id.file_list_img);
            leadName = (TextView) view.findViewById(R.id.lead_name);
            adcontentPeople0 = (TextView) view.findViewById(R.id.adcontent_people_0);
            adcontentTools0 = (TextView) view.findViewById(R.id.adcontent_tools_0);
            adcontentPeople1 = (TextView) view.findViewById(R.id.adcontent_people_1);
            adcontentTools1 = (TextView) view.findViewById(R.id.adcontent_tools_1);
            desc = (TextView) view.findViewById(R.id.desc);
            rate = (TextView) view.findViewById(R.id.rate);
            seekBar = (SeekBar) view.findViewById(R.id.seekbar);
            introductionBtn = (Button) view.findViewById(R.id.equity_introduction_btn);
        }
    }
}
