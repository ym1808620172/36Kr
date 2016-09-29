package com.sunhongxu.a36kr.controler.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.model.bean.VideoBean;
import com.sunhongxu.a36kr.utils.ScreenSizeConstants;
import com.sunhongxu.a36kr.utils.VideoRecyclerView;

import java.util.List;


/**
 * Created by dllo on 16/9/26.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private List<VideoBean.DataBean.DataBeans> dataBeanses;
    private Context context;
    MediaController mMediaCtrl;

    private VideoRecyclerView recyclerView;
    private int playPosition = -1;
    private boolean isPaused = false;
    private boolean isPlaying = false;
    private int currentIndex = -1;

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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (dataBeanses.get(position).getGroup() != null) {
            int width = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.WIDTH);
            int height = ScreenSizeConstants.getScreenSize(context, ScreenSizeConstants.ScreenState.HEIGHT);
            if (dataBeanses.get(position).getGroup().getTitle() != null) {
                holder.titleTv.setText(dataBeanses.get(position).getGroup().getTitle());
            }
            Picasso.with(context).load(dataBeanses.get(position).getGroup().getLarge_cover().getUrl_list().get(position%3).getUrl()).into(holder.videoImage);
            Picasso.with(context).load(dataBeanses.get(position).getGroup().getUser().getAvatar_url()).resize(width / 10, height / 10).into(holder.cirImg);
            holder.videoPlayBtn.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.VISIBLE);
            mMediaCtrl = new MediaController(context, false);
            if (currentIndex == position) {
                holder.videoPlayBtn.setVisibility(View.INVISIBLE);
                holder.videoImage.setVisibility(View.INVISIBLE);

                if (isPlaying || playPosition == -1) {
                    if (holder.mVideoView != null) {
                        holder.mVideoView.setVisibility(View.GONE);
                        holder.mVideoView.stopPlayback();
                        holder.mProgressBar.setVisibility(View.GONE);
                    }
                }

                holder.mVideoView.setVisibility(View.VISIBLE);
                mMediaCtrl.setAnchorView(holder.mVideoView);
                mMediaCtrl.setMediaPlayer(holder.mVideoView);
                mMediaCtrl.setForegroundGravity(Gravity.BOTTOM);
                holder.mVideoView.setMediaController(mMediaCtrl);
                holder.mVideoView.requestFocus();
                holder.mProgressBar.setVisibility(View.VISIBLE);
                if (playPosition > 0 && isPaused) {
                    holder.mVideoView.start();
                    isPaused = false;
                    isPlaying = true;
                    holder.mProgressBar.setVisibility(View.GONE);
                } else {
                    holder.mVideoView.setVideoPath(dataBeanses.get(position).getGroup().getMp4_url());
                    isPaused = false;
                    isPlaying = true;
                }
                holder.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (holder.mVideoView != null) {
                            holder.mVideoView.seekTo(0);
                            holder.mVideoView.stopPlayback();
                            currentIndex = -1;
                            isPaused = false;
                            isPlaying = false;
                            holder.mProgressBar.setVisibility(View.GONE);
                            notifyDataSetChanged();
                        }
                    }
                });
                holder.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.mProgressBar.setVisibility(View.GONE);
                        holder.mVideoView.start();
                    }
                });

            } else {
                holder.videoPlayBtn.setVisibility(View.VISIBLE);
                holder.videoImage.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
            }

            holder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex =position;
                    playPosition = -1;
                    notifyDataSetChanged();
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
        private final VideoView mVideoView;
        private final ImageView videoImage;
        private final ImageButton videoPlayBtn;
        private final ProgressBar mProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            cirImg = (ImageView) itemView.findViewById(R.id.video_cir_img);
            nameTv = (TextView) itemView.findViewById(R.id.video_name_tv);
            titleTv = (TextView) itemView.findViewById(R.id.video_title_tv);
            typeTv = (TextView) itemView.findViewById(R.id.video_type_tv);

            videoImage = (ImageView) itemView.findViewById(R.id.video_image);
            videoPlayBtn = (ImageButton) itemView.findViewById(R.id.video_play_btn);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            mVideoView = (VideoView) itemView.findViewById(R.id.videoview);
        }
    }
}
