package com.ricer.treasureboss.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by Ricer on 2018/1/6.
 * 使用栈管理所有Activity，添加、删除指定、删除当前、移除所有、返回栈大小
 */

class ActivityManager {

    //初始化活动栈
    private Stack<Activity> activitieStack = new Stack<>();
    //饿汉式单例
    private static ActivityManager activityManager = new ActivityManager();

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        return activityManager;
    }

    public void add(Activity activity) {
        if (activity != null) {
            activitieStack.add(activity);
        }
    }

    public void remove(Activity activity) {
        if (activity != null) {
            for (int i = activitieStack.size() - 1; i >= 0; i--) {
                Activity currentActivity = activitieStack.get(i);
                if (currentActivity.getClass().equals(activity.getClass())) {
                    currentActivity.finish();
                    activitieStack.remove(currentActivity); //从栈空间移除

                }
            }
        }
    }

    public void removeCurrent() {
        /*//1.
        Activity activity = activitieStack.get(activitieStack.size() - 1);
        activity.finish();
        activitieStack.remove(activitieStack.size() - 1);*/

        //2.
        Activity activity = activitieStack.lastElement();
        activity.finish();
        activitieStack.remove(activity);

    }

    //删除所有的activity
    public void removeAll() {
        for (int i = activitieStack.size() - 1; i >= 0; i--) {
            Activity activity = activitieStack.get(i);
            activity.finish();
            activitieStack.remove(activity);
        }
    }

    public int size() {
        return activitieStack.size();
    }

}
