package com.kzb.parents.view.pushview;//package com.xrz.view;
//
//import android.app.Activity;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by wanghaofei on 16/9/20.
// */
//public class PerspectiveLoading {
//
//    private List<Integer> threadIds = new ArrayList<Integer>();
//
//    private Activity mActivity;
//
//    public View loading;
//
//    public PerspectiveLoading(Activity mActivity) {
//        this.mActivity = mActivity;
//        threadIds.clear();
//    }
//
//    /**
//     * @param isNeedToMinusTitleHeight isNeedToMinusTitleHeight 是否要减去Title的高度，默认减去TitleBar高度为45dp
//     */
//    public void addLoading(boolean isNeedToMinusTitleHeight) {
//        initLoading(isNeedToMinusTitleHeight, 45);
//    }
//
//    /**
//     * @param titleHeightOfDip titleHeightOfDip：要减去的Title的高度
//     */
//    public void addLoading(int titleHeightOfDip) {
//        initLoading(true, titleHeightOfDip);
//    }
//
//    public void initLoading(boolean isNeedToMinusTitleHeight, int titleHeightOfDip) {
//        if (loading == null) {
//            ViewGroup contentView = (ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
//            int h = -1;
//            if (isNeedToMinusTitleHeight) {
//                h = MyApplication.HEIGHT - ZbjConvertUtils.dip2px(mActivity, titleHeightOfDip) - getStatusHeight(mActivity);
//            } else {
//                h = MyApplication.HEIGHT - getStatusHeight(mActivity);
//            }
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-1, h);
//            params.gravity = Gravity.BOTTOM;
//            loading = LayoutInflater.from(mActivity).inflate(R.layout.common_loading, contentView, false);
//            loading.setLayoutParams(params);
//            contentView.addView(loading);
//        }
//        loading.setClickable(true);
//        hideLoading();
//    }
//
//    public static int getStatusHeight(Activity context) {
//        Class<?> c = null;
//        Object obj = null;
//        Field field = null;
//        int x = 0, sbar = 0;
//        try {
//            c = Class.forName("com.android.internal.R$dimen");
//            obj = c.newInstance();
//            field = c.getField("status_bar_height");
//            x = Integer.parseInt(field.get(obj).toString());
//            sbar = context.getResources().getDimensionPixelSize(x);
//        } catch (Exception ignored) {
//        }
//        return sbar;
//    }
//
//    /**
//     * 关闭加载进度
//     */
//    public void hideLoading() {
//        if (loading != null) {
//            loading.setVisibility(View.GONE);
//        }
//    }
//
//    /**
//     * 关闭加载进度（多线程同时请求）
//     */
//    public void hideLoading(Integer threadId) {
//        if (threadIds.contains(threadId)) {
//            threadIds.remove(threadId);
//        }
//        if (threadIds.size() == 0) {
//            hideLoading();
//        }
//    }
//
//    /**
//     * 显示加载进度
//     */
//    public void showLoading() {
//        if (loading != null) {
//            loading.setVisibility(View.VISIBLE);
//            loading.setClickable(true);
//        }
//    }
//
//    /**
//     * 显示加载进度（多线程同时请求）
//     */
//    public void showLoading(Integer threadId) {
//        showLoading();
//        if (!threadIds.contains(threadId)) {
//            threadIds.add(threadId);
//        }
//    }
//
//}
