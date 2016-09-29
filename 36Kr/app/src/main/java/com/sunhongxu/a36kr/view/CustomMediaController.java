package com.sunhongxu.a36kr.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sunhongxu.a36kr.R;

import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by xhb on 2016/3/1.
 * 自定义视频控制器
 */
public class CustomMediaController extends MediaController {
    private static final int HIDEFRAM = 0;//控制提示窗口的显示
    private GestureDetector mGestureDetector;
    private ImageButton img_back;//返回按钮
    private TextView mFileName;//文件名
    private VideoView videoView;
    private Activity activity;
    private Context context;
    private int controllerWidth = 0;//设置mediaController高度为了使横屏时top显示在屏幕顶端
    private View mVolumeBrightnessLayout;//提示窗口
    private ImageView mOperationBg;//提示图片
    private TextView mOperationTv;//提示文字
    private AudioManager mAudioManager;
    private changeScreen changeScreen;
    private View.OnClickListener backListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (activity != null) {
                activity.finish();
            }
        }
    };
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long pos;
            switch (msg.what) {
                case HIDEFRAM://隐藏提示窗口
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    mOperationTv.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private ImageView img_screen;
    private static String content;

    public interface changeScreen {
        void OnChangeScreen(int index);
    }

    //videoview 用于对视频进行控制的等，activity为了退出
    public CustomMediaController(Context context, VideoView videoView, Activity activity) {
        super(context);
        this.context = context;
        this.videoView = videoView;
        this.activity = activity;
        changeScreen = (changeScreen) context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        controllerWidth = wm.getDefaultDisplay().getWidth();
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected View makeControllerView() {
        View v = LayoutInflater.from(context).inflate(R.layout.media_controller, null);
        v.setMinimumHeight(controllerWidth);
        //获取控件
        img_back = (ImageButton) v.findViewById(R.id.mediacontroller_top_back);
        mFileName = (TextView) v.findViewById(R.id.mediacontroller_filename);
        if (content != null) {
            mFileName.setText(content);
        }
        img_screen = (ImageView) v.findViewById(R.id.mediacontroller_all_screen);
        //声音控制
        mVolumeBrightnessLayout = v.findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView) v.findViewById(R.id.operation_bg);
        mOperationTv = (TextView) v.findViewById(R.id.operation_tv);
        mOperationTv.setVisibility(View.GONE);
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //注册事件监听
        img_back.setOnClickListener(backListener);
        img_screen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!img_screen.isSelected()) {
                    changeScreen.OnChangeScreen(0);
                    img_screen.setSelected(true);
                } else {
                    changeScreen.OnChangeScreen(1);
                    img_screen.setSelected(false);
                }
            }
        });
        return v;
    }

    public static void getTextString(String string) {
        content = string;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("MYApp-MyMediaController-dispatchKeyEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) return true;// 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         * 因为使用的是自定义的mediaController 当显示后，mediaController会铺满屏幕，
         * 所以VideoView的点击事件会被拦截，所以重写控制器的手势事件，
         * 将全部的操作全部写在控制器中，
         * 因为点击事件被控制器拦截，无法传递到下层的VideoView，
         * 所以 原来的单机隐藏会失效，作为代替，
         * 在手势监听中onSingleTapConfirmed（）添加自定义的隐藏/显示，
         */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //当手势结束，并且是单击结束时，控制器隐藏/显示
            toggleMediaControlsVisiblity();
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playOrPause();
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


    /**
     * 隐藏或显示
     */
    private void toggleMediaControlsVisiblity() {
        if (isShowing()) {
            hide();
        } else {
            show();
        }
    }

    /**
     * 播放/暂停
     */
    private void playOrPause() {
        if (videoView != null)
            if (videoView.isPlaying()) {
                videoView.pause();
            } else {
                videoView.start();
            }
    }

}