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

**待完善功能**

- 广告功能  
- 试看功能

**支持格式**

- FLV MP4
- M3U8 RTMP 等其他格式未来版本支持


License

Copyright 2017 mmengchen, Inc.
