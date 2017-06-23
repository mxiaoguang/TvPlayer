package com.mmengchen.tvplayer.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mmengchen.tvplayer.Test;
import com.mmengchen.tvplayer.utils.LogUtils;
import com.mmengchen.tvplayer.utils.StringUtils;
import com.mmengchen.tvplayer.view.TvVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
   /* static {
        System.loadLibrary("native-lib");
    }*/
    TvVideoView videoview;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 3://计时
                    Log.i("myTag","ddddddddd"+StringUtils.generateTime(videoview.getCurrentPosition()));
                    timer.cancel(); //退出计时器
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        timer = new Timer(true);
        Log.i("myTag","dd");
        //timer.schedule(task,1000 ,1000 ); //延时0 ms后执行，playPartTime ms执行一次
//        timer.schedule(task,50000);
    }

    private void initView() {
        videoview = (TvVideoView) findViewById(R.id.act_videoview);
        /*videoview.setVideoPath("http://video.aimengchong.cc/exposure_4.flv");
        testPlayPart(false);/测试试看功能
        testAD(true);
        videoview.start();*/
        Test test = new Test();
//        LogUtils.i("myTag","jni获取数据"+test.stringFromJNI());

    }


    private void testAD(boolean b) {
        videoview.setOpenAD(true);
        videoview.setAD_Time(1000*3);//3秒
//        videoview.setAD_TYPE(TvVideoView.AD_TYPE_IMG);//设置图片广告
        videoview.setAD_TYPE(TvVideoView.AD_TYPE_VIDEO);//设置视频
        List<String> urls = new ArrayList<>();
        urls.add("http://bmob-cdn-7308.b0.upaiyun.com/2016/11/22/76fed5047b5d4ce1b57505199ba540a4.mp4");
        videoview.setAD_VIDEO_URLS(urls);//注意设置视频/图片都需要设置url
        videoview.setAdListener(new TvVideoView.ADListener() {
            @Override
            public void onStart() {
                LogUtils.i("myTag","dddddddd  on start");
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onError(String errMsg) {
                LogUtils.i("myTag","dddddddd  on error"+errMsg);
            }

            @Override
            public void onEnd() {
                Log.i("myTag","广告结束了,当前视频时间为");
            }
        });
    }

    private void testPlayPart(boolean flag) {
        videoview.setOpenPlayPart(false);// flag 为true开启试看功能
        videoview.setPlayPartTime(1);//试看时间为一分钟
        videoview.setPlayPartLister(new TvVideoView.PlayPartLister() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {
                Log.i("myTag","试看结束,时间为"+ StringUtils.generateTime(videoview.getCurrentPosition()));
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
