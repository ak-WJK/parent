package com.kzb.parents.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.kzb.parents.container.MineContainer;
import com.kzb.parents.http.HttpConfig;
import com.kzb.parents.util.LazyUtil;
import com.kzb.parents.util.NetUtils;
import com.kzb.parents.view.DialogView;

import java.lang.reflect.Field;


/**
 * Created by wanghaofei on 16/9/20.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected String color = "#";
    protected boolean isAddView = true;

    public HttpConfig httpConfig;
    public DialogView dialogView;

    protected Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onResume() {
        super.onResume();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        AppManager.getAppManager().addActivity(this);
        MineContainer.getInstance().addActivity(this);

        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            //设置状态栏颜色
//            window.setStatusBarColor(getResources().getColor(R.color.transparent));
//
//
//        }
    }


    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }




    @Override
    protected void onPause() {
        super.onPause();
        LazyUtil.log(getClass().getName(), "     onPause");
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    //初始化view
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    public boolean isNetConnect() {
        return NetUtils.isNetworkAvailable(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LazyUtil.cancelToast(this);
        LazyUtil.log(getClass().getName(), "     onDestroy");
    }



}
