package com.mmengchen.tvplayer.utils;

import android.util.Log;

/**
 * 日志打印的工具类
 * Created by 11655 on 2017/6/15.
 */

public class LogUtils {
    private static boolean onSwitch = true;

    private static String TAG = "TvPlayer";

    public static void setOnSwitch(boolean onSwitch) {
        LogUtils.onSwitch = onSwitch;
    }

    public static void v(String msg) {
        if (onSwitch) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (onSwitch) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (onSwitch) {
            Log.i(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (onSwitch) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String msg, Throwable throwable) {
        if (onSwitch) {
            Log.e(TAG, msg, throwable);
        }
    }

    public static void w(String msg) {
        if (onSwitch) {
            Log.w(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (onSwitch) {
            Log.v(TAG + "---" + tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (onSwitch) {
            Log.d(TAG + "---" + tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (onSwitch) {
            Log.i(TAG + "---" + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (onSwitch) {
            Log.e(TAG + "---" + tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        if (onSwitch) {
            Log.e(TAG + "---" + tag, msg, throwable);
        }
    }

    public static void w(String tag, String msg) {
        if (onSwitch) {
            Log.w(TAG + "---" + tag, msg);
        }
    }
    public static void w(String tag, String msg,Throwable throwable) {
        if (onSwitch) {
            Log.w(TAG + "---" + tag, msg);
        }
    }
}
