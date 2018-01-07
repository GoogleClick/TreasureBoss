package com.ricer.treasureboss.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Ricer on 2018/1/6.
 */

public class MyApplication extends Application {

    //整个应用中用到的变量
    public static Context context;  //当前应用application实例
    public static Handler handler;  //需用到的handler
    public static Thread mainThread;    //当前应用主线程
    public static int mainThreadId; //当前应用主线程Id

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();    //application线程为应用主线程
        mainThreadId = android.os.Process.myTid();  //从系统获取当前线程Id

        //初始化未捕获异常处理器
//        CrashHandler.getInstance().init();
        //初始化ShareSDK
//        ShareSDK.initSDK(this);
    }
}
