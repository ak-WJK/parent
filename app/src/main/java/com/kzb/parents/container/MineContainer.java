package com.kzb.parents.container;

import android.app.Activity;

import com.kzb.baselibrary.log.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/1/12.
 */

public class MineContainer {

    private static MineContainer mineContainer;
    private List<Activity> activityList = new ArrayList<>();

    private MineContainer(){

    }

    public static MineContainer getInstance(){
        if (mineContainer == null){
            synchronized (MineContainer.class){
                if (mineContainer == null) {
                    mineContainer = new MineContainer();
                }
            }
        }
        return mineContainer;
    }




    /**
     * -------------界面生命周期管理 --------------
     **/
    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        Log.d("ZbjContainer:addActivity==>", activity.getClass().getSimpleName());
        activityList.add(activity);
    }

    // 容器中移除Activity
    public void removeActivity(Activity activity) {
        Log.d("ZbjContainer:removeActivity==>", activity.getClass().getSimpleName());
        activityList.remove(activity);
    }

    // 根据名字获取Activity
    public Activity getActivity(String activityName) {
        int size = activityList.size();
        for (int i = 0; i < size; i++) {
            Activity activity = activityList.get(i);
            if (activity.getClass().getSimpleName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }

    // 获取最上层Activity
    public Activity getTopActivity() {
        int size = activityList.size();
        if (size == 0) {
            return null;
        }
        return activityList.get(size - 1);

    }

    // 获取上一个Activity
    public Activity getLastActivity() {
        int size = activityList.size();
        if (size <= 1) {
            return null;
        }
        return activityList.get(size - 2);
    }

    // 是否是最上层Activity
    public boolean isTopActivity(Activity activity) {
        int size = activityList.size();
        if (size == 0) {
            return false;
        }
        Activity topActivity = activityList.get(size - 1);
        return topActivity == activity;
    }

    // 是否是APP启动的第一个页面
//    public boolean isFirstActivity(Activity activity) {
//        if (activity.getClass().getSimpleName().equals(firstActivity)) {
//            return true;
//        }
//        return false;
//    }

    // 关闭所有
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
    }


}



