package com.ricer.treasureboss.common;

/**
 * Created by Ricer on 2018/1/6.
 * 开关Log功能更强大
 * 建议使用开源框架Logger,可直接打印json，xml等
 */

public final class LogUtil {
    public static final String TAG="Treasure Boss";

    //是否打印Log
    private static boolean mbLoggable = true;

    public static void setLoggable(boolean bLoggable)
    {
        mbLoggable = bLoggable;
    }

    public static boolean isLoggable()
    {
        return mbLoggable;
    }

    public static boolean isDebuggable()
    {
        return mbLoggable;
    }

    public static int i(String tag, String msg) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.i(tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.i(tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.d(tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.d(tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.e(tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.e(tag, msg, tr);
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.w(tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        if (!mbLoggable)
            return -1;
        return android.util.Log.w(tag, msg);
    }
}
