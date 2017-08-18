package com.kzb.baselibrary.utils;

import android.content.Context;
import android.widget.Toast;
import android.os.Handler;
/**
 * Created by wanghaofei on 16/7/8.
 */
public class MineToast {

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG  = Toast.LENGTH_LONG;

    private static Toast toast;
    private static Handler handler = new Handler();

    private static Runnable run = new Runnable() {
        @Override
        public void run() {
            toast.cancel();
            toast = null;
        }
    };


    private static void toast(Context ctx,CharSequence msg,int duration){
        if(msg == null){
            return;
        }

        handler.removeCallbacks(run);

        // 默认值为2秒
        switch (duration) {
            case LENGTH_LONG:
                duration = 3000;
                break;
            case LENGTH_SHORT:
                duration = 2000;
                break;
            default:
                duration = 1000;
                break;
        }
        if (null != toast) {
            toast.setText(msg);
        } else {
            toast = Toast.makeText(ctx, msg, duration);
        }
        handler.postDelayed(run, duration);
        toast.show();

    }


    /**
     * 弹出Toast
     *
     * @param ctx
     *            弹出Toast的上下文
     * @param msg
     *            弹出Toast的内容
     * @param duration
     *            弹出Toast的持续时间
     */
    public static void show(Context ctx, CharSequence msg, int duration)
            throws NullPointerException {
        if (null == ctx) {
            return;
        }
        toast(ctx, msg, duration);
    }

    /**
     * 弹出Toast
     *
     * @param ctx
     *            弹出Toast的上下文
     * @param resId
     *            弹出Toast的内容的资源ID
     * @param duration
     *            弹出Toast的持续时间
     */
    public static void show(Context ctx, int resId, int duration) {
        if (null == ctx) {
            return;
        }
        toast(ctx, ctx.getResources().getString(resId), duration);
    }

    /**
     *
     * @param ctx
     *            context
     * @param msg
     *            内容
     * @throws NullPointerException
     */
    public static void show(Context ctx, CharSequence msg) {
        if (null == ctx) {
            return;
        }
        toast(ctx, msg, LENGTH_SHORT);
    }





}


















































