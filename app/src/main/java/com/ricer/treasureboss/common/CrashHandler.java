package com.ricer.treasureboss.common;

import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ricer on 2018/1/6.
 * 未捕获异常处理器     程序中未捕获的全局异常的捕获（单例）
 * <p>
 * 解决两个问题：1.出现异常能够给用户一个友好提示，防止闪退用户懵逼；2.记录奔溃日志，发送异常信息到后台，
 * 后续版本 fix bugs.
 */

class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler = null;

    //系统默认的未捕获异常处理器
    private Thread.UncaughtExceptionHandler defaultUnCaughtExceptionHandler;

    /**
     * 懒汉式单例
     * 系统在一个单独的线程中实例化 未捕获异常处理器，不涉及多线程，使用单例
     *
     * @return CrashHandler
     */
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init() {
        defaultUnCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 一旦出现未捕获异常时调用回调
     *
     * @param thread
     * @param exception
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        new Thread() {
            @Override
            public void run() {
                //只能在主线程中调用Looper方法
                Looper.prepare();
                Toast.makeText(UIUtils.getContext(), "( ´﹀` )礼貌的微笑<br>出现异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        collectionException(exception);

        try {
            ActivityManager.getInstance().removeCurrent();
            //结束当前程序进程
            android.os.Process.killProcess(Process.myPid());
            //结束虚拟机
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //收集异常信息
    private void collectionException(Throwable exception) {
        final String exceptionMsg = exception.getMessage();
        //收集用户具体设备、系统信息
        final String msg = Build.DEVICE + ":" + Build.MODEL + ":" + Build.PRODUCT + ":" + Build.VERSION.SDK_INT;
        new Thread() {
            @Override
            public void run() {
                Log.e(LogUtil.TAG, "exception is:" + exceptionMsg);
                Log.e(LogUtil.TAG, "message is:" + msg);
            }
        }.start();
    }
}
