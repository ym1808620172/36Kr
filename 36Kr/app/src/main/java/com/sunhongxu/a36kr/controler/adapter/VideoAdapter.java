package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.VideoBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;
import com.sunhongxu.a36kr.utils.VideoRecyclerView;

import java.util.List;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by dllo on 16/9/26.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoBean.DataBean.DataBeans> dataBeanses;
    private Context context;
    private VideoRecyclerView recyclerView;

    public void setRecyclerView(VideoRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public VideoAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanses(List<VideoBean.DataBean.DataBeans> dataBeanses) {
        this.dataBeanses = dataBeanses;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_rv, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (dataBeanses.get(position).getGroup() != null) {
            int width = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
            int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
            if (dataBeanses.get(position).getGroup().getTitle() != null) {
                holder.titleTv.setText(dataBeanses.get(position).getGroup().getTitle());
            }
            Picasso.with(context).load(dataBeanses.get(position).getGroup().getUser().getAvatar_url()).resize(width / 10, height / 10).into(holder.cirImg);
            MediaController mediaco = new MediaController(context);
//            VideoView与MediaController进行关联
            holder.videoView.setMediaController(mediaco);
            mediaco.setMediaPlayer(holder.videoView);
            //让VideiView获取焦点
            holder.videoView.requestFocus();
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            holder.nameTv.setText(dataBeanses.get(position).getGroup().getUser().getName());
            holder.typeTv.setText(dataBeanses.get(position).getGroup().getStatus_desc());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    VideoBean.DataBean.DataBeans dataBeans = dataBeanses.get(position);
                    recyclerView.onClickVideoRecyclerView(position, dataBeans);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return dataBeanses != null && dataBeanses.size() > 0 ? dataBeanses.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cirImg;
        private final TextView nameTv;
        private final TextView titleTv;
        private final TextView typeTv;
        private final VideoView videoView;

        public ViewHolder(View itemView) {
            super(itemView);
            cirImg = (ImageView) itemView.findViewById(R.id.video_cir_img);
            nameTv = (TextView) itemView.findViewById(R.id.video_name_tv);
            titleTv = (TextView) itemView.findViewById(R.id.video_title_tv);
            typeTv = (TextView) itemView.findViewById(R.id.video_type_tv);
            videoView = (VideoView) itemView.findViewById(R.id.video_view);
        }
    }
}
