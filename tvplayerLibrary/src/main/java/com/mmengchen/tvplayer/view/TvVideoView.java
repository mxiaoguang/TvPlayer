package com.mmengchen.tvplayer.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mmengchen.tvplayer.R;
import com.mmengchen.tvplayer.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 11655 on 2017/6/14.
 */

public class TvVideoView extends FrameLayout {
    private String TAG = TvVideoView.class.getSimpleName();

    private boolean isOpenPlayPart = false;//是否开启试看功能
    private boolean isOpenAD = false;//是否开启广告功能
    private int playPartTime = 5000 * 60;//试看的时间默认为5分钟(单位分钟)
    private int AD_Time = 5;//默认广告时间为5秒
    private final int AD_TYPE_IMG = 1;
    private final int AD_TYPE_VIDEO = 2;
    private int AD_TYPE;//广告的类型
    private List<String> AD_IMG_URLS = new ArrayList<>();//广告的图片地址
    private List<String> AD_VIDEO_URLS = new ArrayList<>();//广告的视频地址

    private ADListener adListener = null;
    private PlayPartLister playPartLister = null;

    private int PROGRESS = 0;
    private int HIDE = 1;
    private MyVideoView mVideoView;
    private SeekBar mControllerSeekBar;
    private TextView mControllerCurrentTime;
    private TextView mControllerSumTime;
    private TextView mControllerSeekTime;
    private FrameLayout mVideoController;
    private Long s_KB;
    private AudioManager audioManager;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (mVideoView != null) {
                        if (mVideoView.isPlaying()) {
                            mControllerSeekBar.setProgress(mVideoView.getCurrentPosition());
                            mControllerCurrentTime.setText(StringUtils.generateTime(mVideoView.getCurrentPosition()));
                            this.sendEmptyMessageDelayed(
                                    PROGRESS, 1000);
                        } else {
                            mControllerSeekBar.setProgress(mVideoView.getCurrentPosition());
                            mControllerCurrentTime.setText(StringUtils.generateTime(mVideoView.getCurrentPosition()));
                        }
                    }
                    break;
                case 1:
                    mVideoController.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    s_KB = (Long) msg.obj;
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" + s_KB);
                    //progressDialog.setMessage("视频加载中" + StringUtils.getNetString(s_KB) + "...");
                    break;
                case 3://计时
                    task.cancel();
                    timer.cancel(); //退出计时器
                    if (mVideoView != null && mVideoView.isPlaying()) {
                        mVideoView.pause();
                        removeMessages(0);//暂停进度条
                        if (playPartLister != null) {
                            playPartLister.onEnd();//调用试看结束的方法
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 3;
            handler.sendMessage(message);
        }
    };
    private Timer timer;

    public TvVideoView(@NonNull Context context) {
        super(context);
    }

    public TvVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.tv_videoview, this);
        initView(view);
        initSeekBar();
    }

    private void initView(View view) {
        mVideoView = (MyVideoView) view.findViewById(R.id.video_view);
        mVideoController = (FrameLayout) view.findViewById(R.id.video_controller);
        mControllerSeekBar = (SeekBar) view.findViewById(R.id.controller_seekBar);
        mControllerCurrentTime = (TextView) view.findViewById(R.id.controller_current_time);
        mControllerSumTime = (TextView) view.findViewById(R.id.controller_sum_time);
        mControllerSeekTime = (TextView) view.findViewById(R.id.controller_seek_time);
        audioManager = (AudioManager) view.getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }

    /*
    *
    * 初始化视频相关
    *
    * */
    private void initVideo() {
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {//视频开始播放
                            // video started
//                            loadingDialog.cancel();
                            //自定义进度条
                            mControllerSeekBar.setMax(mp.getDuration());//设置进度条的最大值为视频的总长度
                            mControllerSumTime.setText(StringUtils.generateTime(mp.getDuration()));
                            // /*发消息开始刷新进度**/
                            handler.sendEmptyMessage(PROGRESS);
                            if (isOpenPlayPart) {//判断是否为试看
                                if (task != null) {
                                    task.cancel();  //将原任务从队列中移除
                                }
                                task = new TimerTask() {
                                    public void run() {
                                        Message message = new Message();
                                        message.what = 3;
                                        handler.sendMessage(message);
                                    }
                                };
                                //开启计时
                                timer = new Timer(true);
                                timer.schedule(task, TvVideoView.this.getPlayPartTime());//getPlayPartTime秒执行一次
                            }
                        }
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
//                    progressDialog.show();
//                    initNetBar();
                        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                            //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                            if (mp.isPlaying()) {
                                handler.removeMessages(2);
//                                progressDialog.dismiss();
                            }
                        }
                        return false;
                    }

                });
            }
        });
    }

    //设置视频路径
    public void setVideoPath(String url) {
        mVideoView.setVideoPath(url);
    }

    //设置视频控制器
    public void setMediaController() {

    }

    //开始播放视频
    public void start() {
        mVideoView.start();
        initVideo();
    }

    //暂停视频
    public void pause() {
        mVideoView.pause();
    }

    //获取视频总长度
    public long getDuration() {
        return mVideoView.getDuration();
    }

    //获取视频的当前进度
    public long getCurrentPosition() {
        return mVideoView.getCurrentPosition();
    }

    //设置视频的进度
    public void seekTo(int msec) {
        mVideoView.seekTo(msec);
    }

    //播放状态
    public boolean isPlaying() {
        return mVideoView.isPlaying();
    }


    public void setOpenPlayPart(boolean openPlayPart) {
        isOpenPlayPart = openPlayPart;
    }

    public void setOpenAD(boolean openAD) {
        isOpenAD = openAD;
    }

    public int getPlayPartTime() {//加1s 解决一秒误差
        return playPartTime * 60000+1000;
    }

    public void setPlayPartTime(int playPartTime) {
        this.playPartTime = playPartTime;
    }

    public void setAD_TIme(int AD_Time) {
        this.AD_Time = AD_Time;
    }

    public void setAD_TYPE(int AD_TYPE) {
        this.AD_TYPE = AD_TYPE;
    }

    public void setAD_IMG_URLS(List<String> AD_IMG_URLS) {
        this.AD_IMG_URLS = AD_IMG_URLS;
    }

    public void setAD_VIDEO_URLS(List<String> AD_VIDEO_URLS) {
        this.AD_VIDEO_URLS = AD_VIDEO_URLS;
    }

    public void setAdListener(ADListener adListener) {
        this.adListener = adListener;
    }

    public void setPlayPartLister(PlayPartLister playPartLister) {
        this.playPartLister = playPartLister;
    }

    private void initSeekBar() {
        mControllerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "video progress is " + progress
                );
                int position = seekBar.getProgress();
                int sum_position = seekBar.getMax();
                float x = seekBar.getWidth();
                float seekbarWidth = seekBar.getX();
                float y = seekBar.getY();
                float width = (position * x) / sum_position + seekbarWidth;
                Log.i("info---x", x + "");
                Log.i("info---y", y + "");
                mControllerSeekTime.setText(StringUtils.generateTime(seekBar.getProgress()));
                mControllerSeekTime.setX(width - 15);
                mControllerSeekTime.setY(y - 10);
                mControllerSeekTime.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "start progress is " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "end progress is" + seekBar.getProgress());
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Handle automatic focus changes.
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    mVideoController.setVisibility(View.VISIBLE);
                    mControllerSeekBar.requestFocus();
                    handler.removeMessages(PROGRESS);//暂停进度条
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    mVideoController.setVisibility(View.VISIBLE);
                    mControllerSeekBar.requestFocus();
                    handler.removeMessages(PROGRESS);//暂停进度条
                    break;
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (event.hasNoModifiers()) {
                        mVideoView.start();
                        //设置获取焦点
//                        videoView.setFocusable(true);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    if (event.hasNoModifiers()) {
                        mVideoView.pause();
                        //设置获取焦点
//                        videoView.setFocusable(true);
                    }
                    break;
                case KeyEvent.KEYCODE_ENTER:
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    if (event.hasNoModifiers()) {
                        if (mVideoView.isPlaying()) {
                            mVideoView.pause();
                            mVideoView.setFocusable(true);
                        } else {
                            mVideoView.start();
                            //设置获取焦点
                            mVideoView.setFocusable(true);
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_VOLUME_UP://音量加
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_RAISE,
                            AudioManager.FLAG_SHOW_UI);
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN://音量减
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_LOWER,
                            AudioManager.FLAG_SHOW_UI);
                    break;
                case KeyEvent.KEYCODE_BACK:
                    break;
            }
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT && event.getAction() == KeyEvent.ACTION_UP ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT && event.getAction() == KeyEvent.ACTION_UP) {
            // /*发消息开始刷新进度**/
            handler.sendEmptyMessage(PROGRESS);
            mVideoView.seekTo(mControllerSeekBar.getProgress());
            //3秒后隐藏
            handler.sendEmptyMessageDelayed(
                    HIDE, 3000);
        }
        boolean ret = super.dispatchKeyEvent(event);
        return ret;
    }

    public interface ADListener {
        void onStart();

        void onClick();

        void onEnd();
    }

    public interface PlayPartLister {
        //试看开始时候调用
        void onStart();

        //试看结束时候调用
        void onEnd();
    }
}
