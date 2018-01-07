package com.ricer.treasureboss.common;

import android.content.Context;
import android.os.Process;
import android.view.View;

/**
 * Created by Ricer on 2018/1/6.
 */

public class UIUtils {
    public static Context getContext() {
        return MyApplication.context;
    }

    public static android.os.Handler getHandler() {
        return MyApplication.handler;
    }

    public static View getView(int layout) {
        return View.inflate(getContext(), layout, null);
    }

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static String[] getStringArray(int strArrId) {
        return getContext().getResources().getStringArray(strArrId);
    }

    public static int dp2px(int dp) {
        //获取屏幕密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public static void runOnUIThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else
            getHandler().post(runnable);
    }

    private static boolean isInMainThread() {
        int currentThreadId = Process.myTid();
        return currentThreadId == MyApplication.mainThreadId;
    }


}
