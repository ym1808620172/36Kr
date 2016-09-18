package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.SearchBean;

import java.util.List;

/**
 * Created by dllo on 16/9/12.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<SearchBean> datas;

    public void setDatas(List<SearchBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public SearchAdapter(Context context) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SearchBean searchBean = (SearchBean) getItem(position);
        if (searchBean != null) {
            viewHolder.textView.setText(searchBean.getContent());
        }
        return convertView;
    }

    class ViewHolder {
        private TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.search_list_tv);
        }
    }
}
