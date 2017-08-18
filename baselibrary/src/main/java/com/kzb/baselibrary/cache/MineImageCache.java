package com.kzb.baselibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kzb.baselibrary.utils.MineSchedulers;
import com.kzb.baselibrary.utils.MineSchedulers.ScheduleCallBack;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wanghaofei on 16/9/23.
 */
public class MineImageCache {
    private static MineImageCache instance;
    // 通知UI线程图片获取ok时使用
    private Paint textBgPaint;
    private Paint textPaint;
    private ImageLoadingListener listener;
    private boolean isDebug;

    public void setDebug(boolean isDebug)
    {
        this.isDebug = isDebug;
        if (isDebug) {
            textBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置画笔背景
            textBgPaint.setColor(Color.BLACK);// 采用的颜色
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 设置画笔
            textPaint.setTextSize(25);// 字体大小
            textPaint.setColor(Color.WHITE);// 采用的颜色
        }
    }
    /**
     * 初始化
     */
    public void init(Context context) {
        ImageUtils.Init(context);
        listener = new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

            }

            @Override
            public void onLoadingComplete(final String url, final View view,
                                          final Bitmap bitmap) {
                if(view!=null){
                    view.setBackgroundColor(0);
                    ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_XY);
                }
                if (isDebug) {
                    MineSchedulers.subscribeOn(MineSchedulers.IO)
                            .callback(new ScheduleCallBack() {

                                @Override
                                public void onCallBack(Object result) {
                                    addLogo((ImageView) view, bitmap,
                                            ((Long) result).longValue());

                                }
                            }).run(new MineSchedulers.SRunnable<Long>() {

                        @Override
                        public Long callable() {
                            return Long.valueOf(getImageSize(url));
                        }
                    });
                }
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {

            }
        };
    }

    public static MineImageCache getInstance() {
        if (instance == null) {
            synchronized (MineImageCache.class) {
                if (instance == null) {
                    instance = new MineImageCache();
                }
            }
        }
        return instance;
    }

    // 下载图片
    public void downloadImage(final View view, String url, int defaultRes) {
        ImageView imageView=new ImageView(view.getContext());
        if(defaultRes>0){
            view.setBackgroundResource(defaultRes);
        }
        downloadImage(imageView, url, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                arg1=null;
                view.setBackgroundDrawable(new BitmapDrawable(arg2));
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {

            }
        }, defaultRes);
    }

    // 下载图片
    public void downloadImage(final ViewGroup view, String url, int defaultRes) {
        ImageView imageView=new ImageView(view.getContext());
        if(defaultRes>0){
            view.setBackgroundResource(defaultRes);
        }
        downloadImage(imageView, url, new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {

            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                arg1=null;
                view.setBackgroundDrawable(new BitmapDrawable(arg2));
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {

            }
        }, defaultRes);
    }

    // 下载图片
    public void downloadImage(ImageView view, String url, int defaultRes) {
        downloadImage(view, url, null, defaultRes);
    }

    // 下载图片
    public void downloadImage(ImageView view, String url,
                              ImageLoadingListener listener, int defaultRes) {
        ImageUtils.displayImage(view, url, defaultRes, false, listener);
    }

    /** ---- **/
    // 下载大图片
    public void downloadBigImage(ImageView view, String url) {
        downloadBigImage(view, url, 0);
    }

    public void downloadBigImage(ImageView view, String url, int defaultRes) {
        ImageUtils.displayBigImage(view, url, defaultRes, false,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {

                    }

                    @Override
                    public void onLoadingComplete(final String url,
                                                  final View view, final Bitmap bitmap) {
                        if (isDebug) {
                            MineSchedulers.subscribeOn(MineSchedulers.IO)
                                    .callback(new ScheduleCallBack() {

                                        @Override
                                        public void onCallBack(Object result) {
                                            addLogo((ImageView) view, bitmap,
                                                    ((Long) result).longValue());

                                        }
                                    }).run(new MineSchedulers.SRunnable<Long>() {

                                @Override
                                public Long callable() {
                                    return Long
                                            .valueOf(getImageSize(url));
                                }
                            });
                        }
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {

                    }
                });
    }

    // 下载小图片
    public void downloadSmallImage(ImageView view, String url) {
        downloadSmallImage(view, url, 0);
    }

    public void downloadSmallImage(ImageView view, String url, int defaultRes) {
        view.setImageBitmap(null);
        view.setBackgroundColor(Color.rgb(248, 248, 248));
        ImageUtils.displaySmallImage(view, url, defaultRes, false, listener);
    }

    // 下载详情图片
    public void downloadInfoImage(ImageView view, String url) {
        view.setImageBitmap(null);
        view.setBackgroundColor(Color.rgb(248, 248, 248));
        ImageUtils.displayImage(view, url, 0, false, listener);
    }

    // 下载广告图片
    public void downloadAdverImage(ImageView view, String url) {
        view.setBackgroundColor(0);
        ImageUtils.displayImage(view, url, 0, false, listener);
    }

    private long getImageSize(String urlString) {
        long lenght = 0;
        try {
            URL mUrl = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            // 判断请求是否成功处理
            if (responseCode == 200) {
                lenght = conn.getContentLength();
            }
            conn.disconnect();
        } catch (Exception e) {
            Log.d("--------", "getImageSize");
        }
        return lenght;
    }

    private void addLogo(ImageView image, Bitmap bitmap, long size) {
        if (image == null || bitmap == null || bitmap.isRecycled()) {
            return;
        }
        String str = size / 1024 + "KB";
        int width = bitmap.getWidth();
        int hight = bitmap.getHeight();
        try {
            Bitmap logoBitmap = Bitmap.createBitmap(width, hight,
                    Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
            Canvas canvas = new Canvas(logoBitmap);// 初始化画布 绘制的图像到icon上
            canvas.drawBitmap(bitmap, 0, 0, null);
            int baseX = width / 3;
            float baseY = hight;
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            canvas.drawRect(new Rect(baseX, (int) (baseY + fontMetrics.top),
                    baseX + (int) textPaint.measureText(str),
                    (int) (baseY + fontMetrics.bottom)), textBgPaint);
            canvas.drawText(str, baseX, baseY, textPaint);// 绘制上去
            // 字，开始未知x,y采用那只笔绘制
            image.setImageBitmap(logoBitmap);
        } catch (OutOfMemoryError e) {

        }
    }
}
