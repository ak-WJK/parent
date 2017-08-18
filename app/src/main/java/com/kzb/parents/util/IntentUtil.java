package com.kzb.parents.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/********************
 * 作者：malus
 * 日期：16/11/30
 * 时间：下午9:42
 * 注释：
 ********************/
public class IntentUtil {
    public static void startActivity(Activity activity, Class clazz) {
        startActivity(activity,clazz,null);
    }
    public static void startActivity(Activity activity,Class clazz,Map<String,String> params) {
        Intent intent = new Intent(activity,clazz);
        if (params != null) {
            Set<String> set = params.keySet();
            Iterator<String> iterator = set.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                intent.putExtra(key,params.get(key));
            }
        }
        activity.startActivity(intent);
    }
    public static void startActivity(Activity activity,Intent intent) {
        activity.startActivity(intent);
    }
    public static void startActivityWithFinish(Activity activity,Intent intent) {
        activity.startActivity(intent);
        finish(activity);
    }
    public static void finish(Activity activity){
        activity.finish();
    }
}
