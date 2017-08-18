package com.kzb.parents.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
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
import java.util.HashMap;
import java.util.Map;


/********************
 * 作者：malus
 * 日期：16/12/6
 * 时间：下午8:41
 * 注释：使用html的textView
 ********************/
public class TempQuesTextView extends TextView {
    Map<String, String> map = new HashMap<>();
    private String mText;

    public TempQuesTextView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_NONE, null);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public TempQuesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_NONE, null);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public TempQuesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_NONE, null);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + DensityUtil.dip2px(getContext(), 10), MeasureSpec.AT_MOST);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public void setNetText(String text) {

        String ans = text.replaceAll("<br>", "");
        ans = ans.replaceAll("&lt;", "<");
        ans = ans.replaceAll("&gt;", ">");

        ans = ans.replaceAll("\\r\\n", "");
        ans = ans.replaceAll("<p>", "");
        ans = ans.replaceAll("</p>", "");
        ans = ans.replaceAll("<br>", "");
        ans = ans.replaceAll("</br>", "");

        map.put("text", ans);
        mText = ans;
        setText(Html.fromHtml(ans, new AnswerHttpImageGeter(), null));
    }


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
                // 存在即获取drawable
                drawable = Drawable.createFromPath(file.getAbsolutePath()); // 获取网路图片
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 4, drawable.getIntrinsicHeight() * 4);
                }
            } else {
            // 不存在即开启异步任务加载网络图片
                AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
                networkPic.execute(source);
            }
            return drawable;
        }
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
                URL url = new URL("http://t.kaozhibao.com" + path);

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

}

