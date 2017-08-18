package com.kzb.baselibrary.log;


import com.kzb.baselibrary.config.MineConfig;

/**
 * Created by wanghaofei on 16/7/8.
 */
public class Log {

    public static final void e(String tag,String msg){
        if(MineConfig.DEBUG){
            android.util.Log.e(tag,msg+"");
        }
    }

    public static final void e(String tag,String msg,Throwable e){
        if(MineConfig.DEBUG){
            android.util.Log.e(tag,msg+"",e);
        }
    }

    public static final void v(String tag,String msg){
        if(MineConfig.DEBUG){
            android.util.Log.v(tag,msg+"");
        }
    }


    public static final void v(String tag,String msg,Throwable e){
        if(MineConfig.DEBUG){
            android.util.Log.v(tag,msg+"",e);
        }
    }

    public static final void i(String tag,String msg){
        if (MineConfig.DEBUG){
            android.util.Log.i(tag,msg);
        }
    }

    public static final void i(String tag,String msg,Throwable e){
        if (MineConfig.DEBUG){
            android.util.Log.i(tag,msg,e);
        }
    }



    public static final void d(String tag, String msg) {
        if (MineConfig.DEBUG) {

            android.util.Log.d(tag, msg + "");
        }
    }

    public static final void d(String tag, String msg, Throwable e) {
        if (MineConfig.DEBUG) {

            android.util.Log.d(tag, msg + "", e);
        }
    }

    public static final void w(String tag, String msg) {
        if (MineConfig.DEBUG) {

            android.util.Log.w(tag, msg + "");
        }
    }

    public static final void w(String tag, String msg, Throwable e) {
        if (MineConfig.DEBUG) {

            android.util.Log.w(tag, msg + "", e);
        }
    }







}













































