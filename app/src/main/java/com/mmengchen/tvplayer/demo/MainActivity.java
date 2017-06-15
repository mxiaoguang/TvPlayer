package com.mmengchen.tvplayer.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mmengchen.tvplayer.view.TvVideoView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
   /* static {
        System.loadLibrary("native-lib");
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        TvVideoView videoview = (TvVideoView) findViewById(R.id.act_videoview);
        videoview.setVideoPath("http://video.aimengchong.cc/exposure_4.flv");
        videoview.start();

      /*  VideoView videoView = (VideoView) findViewById(R.id.act_videovidew);
        videoView.setVideoPath("http://video.aimengchong.cc/petkit-G1.flv");
        videoView.start();*/
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    public native String stringFromJNI();
}
