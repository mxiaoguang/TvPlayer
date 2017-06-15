# TvPlayer
This is VideoLib for Android TV

电视上的视频框架

---

######开发中......


######未经允许请不要投入商业使用
下载或使用 

Download the latest JAR or grab via Maven:

    `<dependency>   
     <groupId>com.mmengchen.tvplayer</groupId>
     <artifactId>tvplayer</artifactId>
     <version>1.0.0</version>
     </dependency>`

or Gradle:
   
 `compile 'com.mmengchen.tvplayer:tvplayer:1.0.0'`

    注：(暂时未支持这种方式)


使用方法

    在布局中使用
        <com.mmengchen.tvplayer.view.TvVideoView
        android:id="@+id/act_videoview"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        </com.mmengchen.tvplayer.view.TvVideoView>


---
**完成的功能**

- 电视上的视频进度条的优化
- 试看功能
- 广告功能
-
     
###试看功能  使用方式

在videoview start() 方法前设置以下三行代码 当试看开始时候被调用，当试看结束之后onEnd 方法被调用   暂时不支持自定义试看视频连接

        videoview.setOpenPlayPart(true);//开启试看功能
        videoview.setPlayPartTime(1);//试看时间为一分钟
        videoview.setPlayPartLister(new TvVideoView.PlayPartLister() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {
                Log.i("myTag","试看结束,时间为"+
                StringUtils.generateTime(videoview.getCurrentPosition()));
            }
        });`

###广告功能使用方式

在videoview start() 方法前设置以下三行代码 当试看开始时候被调用，当试看结束之后onEnd 方法被调用   暂时不支持自定义试看视频连接

        videoview.setOpenAD(true);//设置开启广告
        videoview.setAD_Time(1000*60);//设置广告时间
        videoview.setAD_TYPE(TvVideoView.AD_TYPE_IMG);//设置类型为图片广告
        videoview.setADURLS = "";//设置广告连接
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
**待完善功能**

- 广告功能  （2017.06.15加入，未完善）
- 试看功能  （2017.06.15完成）
- 日志功能

**支持格式**

- FLV MP4 支持
- M3U8 RTMP 等其他格式未来版本支持


License

Copyright 2017 mmengchen, Inc.
