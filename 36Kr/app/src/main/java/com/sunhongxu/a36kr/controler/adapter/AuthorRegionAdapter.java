package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.AuthorRegionBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;

import java.util.List;

/**
 * Created by dllo on 16/9/21.
 */
public class AuthorRegionAdapter extends BaseAdapter {
    private Context context;
    private List<AuthorRegionBean.DataBean.LatestArticleBean> articleBeen;

    public AuthorRegionAdapter(Context context) {
        this.context = context;
    }

    public void setArticleBeen(List<AuthorRegionBean.DataBean.LatestArticleBean> articleBeen) {
        this.articleBeen = articleBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articleBeen != null && articleBeen.size() > 0 ? articleBeen.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return articleBeen != null && articleBeen.size() > 0 ? articleBeen.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_author_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AuthorRegionBean.DataBean.LatestArticleBean article = articleBeen.get(position);
        int width = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
        int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
        Picasso.with(context).load(article.getFeatureImg()).resize(width / 6, height / 10).into(viewHolder.imageView);
        viewHolder.textView.setText(article.getSummary());
        return convertView;
    }

    class ViewHolder {

        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.item_author_tv);
            imageView = (ImageView) view.findViewById(R.id.item_author_img);
        }
    }
}
