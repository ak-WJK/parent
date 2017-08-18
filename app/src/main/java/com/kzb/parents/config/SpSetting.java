package com.kzb.parents.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.kzb.parents.login.model.LoginResponse.LoginModel;
import com.kzb.baselibrary.utils.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by wanghaofei on 16/10/20.
 */

public class SpSetting {

    public static String APP = "kzbp";
    public static SharedPreferences preferences;
    public static Resources resources;

    private static final String TOKEN = "token"; // token

    private static final String LOGIN_INFO = "login_info"; //登录信息
    private static final String COURSE_INFO = "course_info"; //课程信息


    private static Context context;

    public void init(Context context) {

        if (preferences == null) {
//            APP = appName;
            preferences = context.getSharedPreferences(APP, Context.MODE_PRIVATE);
        }
//        if(resolver == null){
//            resolver = context.getContentResolver();
//        }
        if (resources == null) {
            resources = context.getResources();
        }

        this.context = context;
    }


//
//
//    public static void saveToken(String token) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(TOKEN, token);
//        editor.apply();
//    }


//    public static String loadToken() {
//        String token = preferences.getString(TOKEN, null);
////        if(null == token){
////            Application.jumpLogin((BaseActivity)context);
////            return null;
////        }
//
//        return token;
//    }


//    //存储课程信息
//    public static void saveCourseInfo(CourseModel user) {
//
//        try {
//            if (user == null) {
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(COURSE_INFO, null);
//                editor.apply();
//            } else {
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(baos);
//                oos.writeObject(user);
//
//                String user64 = Base64.encodeBytes(baos.toByteArray());
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString(COURSE_INFO, user64);
//                editor.apply();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //加载课程信息
//    public static CourseModel loadCourseInfo() {
//        CourseModel user = null;
//        try {
//
//            String user64 = preferences.getString(COURSE_INFO, null);
//            if (user64 == null) {
//                return null;
//            }
//            byte[] deUser64 = Base64.decode(user64.getBytes());
//            ByteArrayInputStream bais = new ByteArrayInputStream(deUser64);
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            user = (CourseModel) ois.readObject();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return user;
//
//    }


    //存储登录信息
    public static void saveLoginInfo(LoginModel user) {

//        Log.e("tttt","save="+user.toString());

        try {
            if (user == null) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LOGIN_INFO, null);
                editor.apply();
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(user);

                String user64 = Base64.encodeBytes(baos.toByteArray());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(LOGIN_INFO, user64);
                editor.apply();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //清除登录信息
    public static void clearLoginInfo() {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
    }






    //加载登录信息
    public static LoginModel loadLoginInfo() {
        LoginModel user = null;
        try {

            String user64 = preferences.getString(LOGIN_INFO, null);
            if (user64 == null) {
                return null;
            }
            byte[] deUser64 = Base64.decode(user64.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(deUser64);
            ObjectInputStream ois = new ObjectInputStream(bais);
            user = (LoginModel) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }


}
