package com.kzb.parents.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kzb.baselibrary.utils.Base64;
import com.kzb.parents.util.DensityUtil;
import com.kzb.parents.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/********************
 * 作者：malus
 * 日期：16/12/6
 * 时间：下午8:41
 * 注释：使用html的textView
 ********************/


public class QuesTextView extends TextView {
    Map<String, String> map = new HashMap<>();
    private String mText;
    private int mHeight;
    private int lineHeight;
    private boolean needFilter = false;
    private int fLineHeight;
    private boolean needExpandHeight = false;
    private int expandLevel = 1;


    public QuesTextView setExpandLevel(int expandLevel) {
        this.expandLevel = expandLevel;
        return this;
    }

    public boolean isNeedExpandHeight() {
        return needExpandHeight;
    }

    public QuesTextView setNeedExpandHeight(boolean needExpandHeight) {
        this.needExpandHeight = needExpandHeight;
        return this;
    }

    public QuesTextView(Context context) {
        super(context);
//        setLayerType(LAYER_TYPE_NONE, null);
//        setGravity(Gravity.CENTER_VERTICAL);
    }

    public QuesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

//        setLayerType(LAYER_TYPE_NONE, null);
//        setGravity(Gravity.CENTER_VERTICAL);
    }

//    public QuesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        setLayerType(LAYER_TYPE_NONE, null);
////        setGravity(Gravity.CENTER_VERTICAL);
//    }

    OnMeasureListener onMeasureListener;

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public interface OnMeasureListener {
        void onMeasure(int width, int height);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (onMeasureListener != null) {
            onMeasureListener.onMeasure(getMeasuredWidth(), getMeasuredHeight());
        }
        if (needExpandHeight) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + DensityUtil.dip2px(getContext(), 2 * expandLevel), MeasureSpec.AT_MOST);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        //  mHeight+=DensityUtil.dip2px(getContext(),20);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


//        if (mHeight > getMeasuredHeight()) {
//        int mH = getMeasuredHeight();
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mH + DensityUtil.dip2px(getContext(), 15), MeasureSpec.AT_MOST);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//        } else {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + DensityUtil.dip2px(getContext(), 20), MeasureSpec.AT_MOST);
//            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//        }
//        lineHeight = getLineHeight();
    }

    public void setNetText(String text) {
        sizes.clear();

        //data = ansData(data);


        String ans = text;
        if (needFilter) {
            ans = text.replaceAll("<br>", "");
            ans = ans.replaceAll("&lt;", "<");
            ans = ans.replaceAll("&gt;", ">");
            ans = ans.replaceAll("\\r\\n", "");
            ans = ans.replaceAll("<p>", "");
            ans = ans.replaceAll("</p>", "");
            ans = ans.replaceAll("<br>", "");
            ans = ans.replaceAll("</br>", "");
            ans = ans.replaceAll("<div>", "");
            ans = ans.replaceAll("</div>", "");
        }

//        ans = ans.replaceAll("<img", "<img style=\"vertical-align:middle;\" width=\"178\" height=\"91\"");
        ans = ans.replaceAll("&nbsp;", "");
        if (!ans.contains("http://t.kaozhibao.com/Public/ewebeditor")) {
            ans = ans.replace("/Public/ewebeditor", "http://t.kaozhibao.com/Public/ewebeditor");
        }

        int index = 0;

        //设置字体大小
        while ((index = ans.indexOf("font-size:", index)) > 0) {
            int startIndex = ans.indexOf(">", index) + 1;
            int stopIndex = ans.indexOf("<", index);
            if (startIndex > 0 && stopIndex >= startIndex && startIndex <= ans.length()) {
                String con = ans.substring(startIndex, stopIndex);

                String style = ans.substring(index, startIndex);

                if (style != null) {
                    style = style.split(":")[1];
                    if (style != null) {
                        style = style.split("pt")[0];
                        if (style != null) {
                            //去除空格
                            style = style.replace(" ", "");
                            if (style != null) {
                                try {
                                    int size = Integer.parseInt(style);
                                    if (size < 6) {
                                        ans = ans.substring(0, startIndex) + "<small>" + con + "</small>" + ans.substring(stopIndex, ans.length());
//                                        ans = ans.substring(0, startIndex) + "<small><small>" + con + "</small></small>" + ans.substring(stopIndex, ans.length());
                                    } else if (size < 12) {
                                        ans = ans.substring(0, startIndex) + con + ans.substring(stopIndex, ans.length());
//                                        ans = ans.substring(0, startIndex) + "<small>" + con + "</small>" + ans.substring(stopIndex, ans.length());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            index++;
        }
        index = 0;
        while ((index = ans.indexOf("style=", index)) > 0) {
            index++;
            int startIndex = ans.indexOf("\"", index);
            int stopIndex = ans.indexOf("\"", startIndex + 1);
            if (startIndex > 0 && stopIndex > startIndex) {
                String style = ans.substring(startIndex + 1, stopIndex);
                if (style.contains("width") && style.contains("height")) {
                    String width = style.split("width")[1];
                    width = width.split(";")[0];
                    width = width.replaceAll(":", "");
                    width = width.replaceAll(" ", "");

                    String height = style.split("height")[1];
                    height = height.split(";")[0];
                    height = height.replaceAll(":", "");
                    height = height.replaceAll(" ", "");

                    ans = ans.substring(0, stopIndex + 1) + " width=\"" + width + "\"" + " height=\"" + height + "\"" + ans.substring(stopIndex + 1, ans.length());
                }
            }

        }
        ans = ans.replaceAll("px", "");

        index = 0;
        while ((index = ans.indexOf("<img", index)) > 0) {

            int startIndex = ans.indexOf("<img", index);
            int stopIndex = ans.indexOf(">", index);
            index++;
            String content = ans.substring(startIndex, stopIndex + 1);
            if (content.contains("width") && content.contains("height")) {
                String width = content.split("width=")[1];
                if (!TextUtils.isEmpty(width)) {
                    if (!TextUtils.isEmpty(width)) {
                        int startW = width.indexOf("\"");
                        int stopW = width.indexOf("\"", startW + 1);
                        if (startW >= 0 && stopW > startW) {
                            width = width.substring(startW + 1, stopW);

                            String height = content.split("height=")[1];
                            int startH = height.indexOf("\"");
                            int stopH = height.indexOf("\"", startH + 1);
                            height = height.substring(startH + 1, stopH);

                            String src = content.split("src=")[1];
                            int startS = src.indexOf("\"");
                            int stopS = src.indexOf("\"", startS + 1);
                            src = src.substring(startS + 1, stopS);

                            Map<String, String> map = new HashMap<>();
                            map.put("width", width);
                            map.put("height", height);
                            map.put("src", src);
                            sizes.add(map);
                        }

                    }

                }

            }
        }


        map.put("text", ans);
        mText = ans;

        setText(Html.fromHtml(ans, new AnswerHttpImageGeter(), null));
    }

    private List<Map<String, String>> sizes = new ArrayList<>();

    /**
     * ImageGetter 加载网络图片
     */
    public class AnswerHttpImageGeter implements Html.ImageGetter {
        @Override
        public Drawable getDrawable(String source) {
            Drawable drawable = null;

            source = source.replace("\\", "");
            source = source.replace("\"", "");

            String picName = Base64.encodeBytes(source.getBytes());
            // 封装路径
//            File file = new File(Environment.getExternalStorageDirectory(), picName);
            File file = new File(FileUtil.getCachePath(), picName);
            // 判断是否以http开头
            // 判断路径是否存在
            if (file.exists()) {
//                Toast.makeText(getContext(),file.getPath(),Toast.LENGTH_LONG).show();
                // 存在即获取drawable
                drawable = Drawable.createFromPath(file.getAbsolutePath()); // 获取网路图片


//                BitmapFactory.Options options = new BitmapFactory.Options();
                //下面一句话只获取宽高等信息不加载到内存中;
                //options.inJustDecodeBounds = true;
//                options.inSampleSize = 10;


// 设置参数
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
//                BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//                int height1 = options.outHeight;
//                int width1 = options.outWidth;
//                int inSampleSize = 1; // 默认像素压缩比例，压缩为原图的1/2
//                int minLen = Math.min(height1, width1); // 原图的最小边长
//                if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
//                    float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
//                    inSampleSize = (int) ratio;
//                }
//                options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
//                options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
//                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options); // 解码文件


                final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());


                if (drawable != null) {

                    boolean hasSize = false;
                    Map<String, String> sizeMap = new HashMap();

                    for (Map map : sizes) {
                        if (map.get("src").equals(source)) {
                            hasSize = true;
                            sizeMap = map;
                        }
                    }

                    lineHeight = getLineHeight();
                    // drawable.setAlpha(1);
                    int width = drawable.getIntrinsicWidth();
                    int height = drawable.getIntrinsicHeight();
                    if (hasSize) {
                        width = Integer.parseInt(sizeMap.get("width"));
                        height = Integer.parseInt(sizeMap.get("height"));
                    }

                    //设置点击图标进入解析题目显示
                    width = (int) (DensityUtil.dip2px(getContext(), width - 30));
                    height = (int) ((DensityUtil.dip2px(getContext(), height - 2)));
                    //图片太小的时候
                    if (lineHeight > height) {
                        lineHeight += DensityUtil.dip2px(getContext(), 8);
                        final int sHeigh = height;
                        final double sWidth = width;
                        drawable = new Drawable() {
                            @Override
                            public void draw(Canvas canvas) {
                                canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

                                Rect mSrcRect = new Rect(0, 0, (int) sWidth, lineHeight);
                                Rect mDestRect = new Rect(0, (int) ((lineHeight - sHeigh) / 2), (int) sWidth, sHeigh + (lineHeight - sHeigh) / 2);
//                                Rect mDestRect = new Rect(0, (int) ((lineHeight - sHeigh)), (int) sWidth, sHeigh + (lineHeight - sHeigh));
                                Paint paint = new Paint();
                                paint.setAntiAlias(true);
                                Bitmap newBitmap = zoomImage(bitmap, sWidth, sHeigh);
                                canvas.drawBitmap(newBitmap, mSrcRect, mDestRect, paint);
                            }

                            @Override
                            public void setAlpha(int alpha) {

                            }

                            @Override
                            public void setColorFilter(ColorFilter colorFilter) {

                            }

                            @Override
                            public int getOpacity() {
                                return PixelFormat.UNKNOWN;
                            }
                        };
                        drawable.setBounds(0, 0, (int) width, lineHeight);
                    } else {
                        drawable.setBounds(0, DensityUtil.dip2px(getContext(), 2), width, height + DensityUtil.dip2px(getContext(), 2));
                    }
                }
                invalidate();
            } else {
                // 不存在即开启异步任务加载网络图片
                AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
                networkPic.execute(source);
            }

            return drawable;
        }
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高

        float width = bgimage.getWidth();
        float height = bgimage.getHeight();


        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();


        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        if (scaleHeight != 0 && scaleWidth != 0) {
            // 缩放图片动作
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                    (int) height, matrix, true);
            return bitmap;
        }

        return null;

    }

    /**
     * 加载网络图片异步类
     *
     * @author Susie
     */
    private final class AsyncLoadNetworkPic extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // 加载网络图片
            loadNetPic(params);
            return params[0];
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // 当执行完成后再次为其设置一次
            //问题的
            setText(Html.fromHtml(mText, new AnswerHttpImageGeter(), null));
        }

        /**
         * 加载网络图片
         */
        private void loadNetPic(String... params) {
            String path = params[0];
            String picName = Base64.encodeBytes(path.getBytes());

//            File file = new File(Environment.getExternalStorageDirectory(), picName);
            File file = new File(FileUtil.getCachePath(), picName);

            InputStream in = null;

            FileOutputStream out = null;

            try {
                URL url;
                if (path.startsWith("http")) {
                    url = new URL(path);
                } else {
                    url = new URL("http://t.kaozhibao.com" + path);
                }
                HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();

                connUrl.setConnectTimeout(5000);

                connUrl.setRequestMethod("GET");

                if (connUrl.getResponseCode() == 200) {

                    in = connUrl.getInputStream();

                    out = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];

                    int len;

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class VerticalImageSpan extends ImageSpan {

        public VerticalImageSpan(Drawable drawable) {
            super(drawable);
        }

        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fontMetricsInt) {
            Drawable drawable = getDrawable();
            Rect rect = drawable.getBounds();
            if (fontMetricsInt != null) {
                Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
                int fontHeight = fmPaint.bottom - fmPaint.top;
                int drHeight = rect.bottom - rect.top;

                //对于这里我表示,我不知道为啥是这样。不应该是fontHeight/2?但是只有fontHeight/4才能对齐
                //难道是因为TextView的draw的时候top和bottom是大于实际的？具体请看下图
                //所以fontHeight/4是去除偏差?
                int top = drHeight / 2 - fontHeight / 4;
                int bottom = drHeight / 2 + fontHeight / 4;

                fontMetricsInt.ascent = -bottom;
                fontMetricsInt.top = -bottom;
                fontMetricsInt.bottom = top;
                fontMetricsInt.descent = top;
            }
            return rect.right;
        }


        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end,
                         float x, int top, int y, int bottom, Paint paint) {
            Drawable drawable = getDrawable();
            canvas.save();
            int transY = 0;
            //获得将要显示的文本高度-图片高度除2等居中位置+top(换行情况)
            transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
            canvas.translate(x, transY);
            drawable.draw(canvas);
            canvas.restore();
        }
    }

    public QuesTextView setNeedFilter(boolean need) {
        needFilter = need;
        return this;
    }

    Paint.FontMetricsInt fontMetricsInt;
    boolean adjustTopForAscent = true;

    @Override
    protected void onDraw(Canvas canvas) {
        if (adjustTopForAscent) {//设置是否remove间距，true为remove
            if (fontMetricsInt == null) {
                fontMetricsInt = new Paint.FontMetricsInt();
                getPaint().getFontMetricsInt(fontMetricsInt);
            }
            canvas.translate(0, fontMetricsInt.top - fontMetricsInt.ascent);
        }
        super.onDraw(canvas);
    }


}

