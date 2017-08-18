package com.kzb.baselibrary.config;

import android.app.Application;

/**
 * Created by wanghaofei on 16/7/8.
 */
public class MineConfig {

    public final static String TAG = "mine_frame";

    public static boolean DEBUG;
    //域名
    public static String HOST="";

    // 系统时间
    public static long SYSTEM_TIME;
    // 本地时间
    public static long LOCAL_TIME;

    public static void init(Application application,boolean debug){
        DEBUG = debug;
    }


    public static void initTime(long systime){
        SYSTEM_TIME = systime;
        LOCAL_TIME = System.currentTimeMillis();
    }


    public static long getCurrentTime(){
        long time = 0;
        if (SYSTEM_TIME != 0){
            time = SYSTEM_TIME + System.currentTimeMillis() - LOCAL_TIME;
        }else {
            time = System.currentTimeMillis();
        }
        return time;
    }










}
