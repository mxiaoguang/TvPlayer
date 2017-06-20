package com.mmengchen.tvplayer.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mmengchen.tvplayer.utils.StringUtils;
import com.mmengchen.tvplayer.view.TvVideoView;

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
        videoview.setVideoPath("http://video.aimengchong.cc/exposure_4.flv");
        testPlayPart(false);//测试试看功能
        testAD(true);
        videoview.start();

    }


    private void testAD(boolean b) {
        videoview.setOpenAD(true);
        videoview.setAD_Time(1000*3);//3秒
        videoview.setAD_TYPE(TvVideoView.AD_TYPE_IMG);//设置图片广告
        videoview.setAdListener(new TvVideoView.ADListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onError(String errMsg) {

            }

            @Override
            public void onEnd() {
                Log.i("myTag","广告结束了,当前视频时间为"+ StringUtils.generateTime(videoview.getCurrentPosition()));
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
