package com.kzb.parents.application;

/**
 * Created by wanghaofei on 16/10/13.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.WindowManager;

import com.kzb.baselibrary.cache.ImageUtils;
import com.kzb.baselibrary.config.MineConfig;
import com.kzb.baselibrary.network.OkHttpUtils;
import com.kzb.baselibrary.network.https.HttpsUtils;
import com.kzb.baselibrary.network.log.LoggerInterceptor;
import com.kzb.parents.config.Config;
import com.kzb.parents.config.SpSetting;
import com.kzb.parents.container.MineContainer;
import com.kzb.parents.third.ClearableCookieJar;
import com.kzb.parents.third.PersistentCookieJar;
import com.kzb.parents.util.LogUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;


public class Application extends android.app.Application {

    public static int WIDTH = 0;
    public static int HEIGHT = 0;


    public static   Context mContext;

    public static LogUtils.Builder IBuilder;


    public static String deviceId;


    public static SharedPreferences spTimes;

    @Override
    public void onCreate() {
        super.onCreate();
        spTimes  = getSharedPreferences("usetime", Context.MODE_PRIVATE);

        mContext = getApplicationContext();

//        CrashReport.initCrashReport(getApplicationContext(),"7f6be648e6",false);

        MineConfig.init(this,true);
        new SpSetting().init(this);
        Config.init(1);
        //异常捕获
//        CrashHandler.getInstance().init(this)

        ImageUtils.Init(this);

        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        WIDTH = wm.getDefaultDisplay().getWidth();
        HEIGHT = wm.getDefaultDisplay().getHeight();


        try{
            //得到移动设备(唯一)Id
            deviceId = android.os.Build.SERIAL;
//            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            deviceId = tm.getDeviceId();
        }catch (Exception e){
            deviceId="1111111";
        }





        ClearableCookieJar cookieJar1 = new PersistentCookieJar(new com.kzb.parents.third.cache.SetCookieCache(), new com.kzb.parents.third.persistence.SharedPrefsCookiePersistor(getApplicationContext()));

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);

//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("kzb"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier()
                {
                    @Override
                    public boolean verify(String hostname, SSLSession session)
                    {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);

        //Log注册
        IBuilder = new LogUtils.Builder()
                .setLogSwitch(true)
                .setGlobalTag("TAG")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(LogUtils.V);// log过滤器，和logcat过滤器同理，默认Verbose


    }


    public void exit() {

        MineContainer.getInstance().exit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }, 200);
    }



}
