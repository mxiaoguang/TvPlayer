package com.mmengchen.tvplayer;

/**
 * Created by 11655 on 2017/6/14.
 */

public class Test {
    //载入动态库
    static {
        System.loadLibrary("native-lib");
    }
    public native String stringFromJNI();
}
