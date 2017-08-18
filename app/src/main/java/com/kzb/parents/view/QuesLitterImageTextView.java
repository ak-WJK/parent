package com.kzb.parents.view;//package com.kaozhibao.view;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.text.Html;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.widget.TextView;
//
//import com.kaozhibao.mylibrary.utils.Base64;
//import com.kaozhibao.util.DensityUtil;
//import com.kaozhibao.util.FileUtil;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//
///********************
// * 作者：malus
// * 日期：16/12/6
// * 时间：下午8:41
// * 注释：使用html的textView
// ********************/
//public class QuesLitterImageTextView extends TextView {
//    Map<String, String> map = new HashMap<>();
//    private String mText;
//
//    public QuesLitterImageTextView(Context context) {
//        super(context);
//        setLayerType(LAYER_TYPE_NONE, null);
//        setGravity(Gravity.CENTER_VERTICAL);
//
//        // 这句话是让 在主线程 下载图片 变为可能，没有这句话就报错。。反正我的报错
////        if (Build.VERSION.SDK_INT >= 11) {
////
////            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
////                    .detectDiskReads().detectDiskWrites().detectNetwork()
////                    .penaltyLog().build());
////            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
////                    .detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects()
////                    .penaltyLog().penaltyDeath().build());
////        }
//
//    }
//
//    public QuesLitterImageTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        setLayerType(LAYER_TYPE_NONE, null);
//        setGravity(Gravity.CENTER_VERTICAL);
//        // 这句话是让 在主线程 下载图片 变为可能，没有这句话就报错。。反正我的报错
////        if (Build.VERSION.SDK_INT >= 11) {
////
////            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
////                    .detectDiskReads().detectDiskWrites().detectNetwork()
////                    .penaltyLog().build());
////            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
////                    .detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects()
////                    .penaltyLog().penaltyDeath().build());
////        }
//
//    }
//
//    public QuesLitterImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        setLayerType(LAYER_TYPE_NONE, null);
//        setGravity(Gravity.CENTER_VERTICAL);
//        // 这句话是让 在主线程 下载图片 变为可能，没有这句话就报错。。反正我的报错
////        if (Build.VERSION.SDK_INT >= 11) {
////
////            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
////                    .detectDiskReads().detectDiskWrites().detectNetwork()
////                    .penaltyLog().build());
////            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
////                    .detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects()
////                    .penaltyLog().penaltyDeath().build());
////        }
//
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() + DensityUtil.dip2px(getContext(), 10), MeasureSpec.AT_MOST);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    public void setNetText(String text) {
//
//        String ans = text.replaceAll("<br>", "");
//        ans = ans.replaceAll("&lt;", "<");
//        ans = ans.replaceAll("&gt;", ">");
//
//        ans = ans.replaceAll("\\r\\n", "");
//        ans = ans.replaceAll("<p>", "");
//        ans = ans.replaceAll("</p>", "");
//        ans = ans.replaceAll("<br>", "");
//        ans = ans.replaceAll("</br>", "");
//
////        ans = ans.replaceAll("&lt;p&gt;", "");
////        ans = ans.replaceAll("&lt;/p&gt;", "");
////        ans = ans.replaceAll("&lt;br&gt;", "");
////        ans = ans.replaceAll("&lt;/br&gt;", "");
//
//        map.put("text", ans);
//        mText = ans;
//        setText(Html.fromHtml(ans, new AnswerHttpImageGeter(), null));
////        setText(Html.fromHtml(ans, imageGetter,null));
//    }
//
//
//    /**
//     * ImageGetter 加载网络图片
//     */
//    public class AnswerHttpImageGeter implements Html.ImageGetter {
//        @Override
//        public Drawable getDrawable(String source) {
//            Drawable drawable = null;
//
//            source = source.replace("\\", "");
//            source = source.replace("\"", "");
//
//            String picName = Base64.encodeBytes(source.getBytes());
//            // 封装路径
////            File file = new File(Environment.getExternalStorageDirectory(), picName);
//            File file = new File(FileUtil.getCachePath(), picName);
//
//            // 判断是否以http开头
//            // 判断路径是否存在
//            if (file.exists()) {
//                // 存在即获取drawable
//                drawable = Drawable.createFromPath(file.getAbsolutePath()); // 获取网路图片
//                if (drawable != null) {
//                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 4,(int)(drawable.getIntrinsicHeight() * 2.5));
//                }
//            } else {
////                if (source.endsWith("gif")) {
////                    View view = LayoutInflater.from(getContext()).inflate(R.layout.bitmap_webview,null);
////                    WebView webView = (WebView)view.findViewById(R.id.webview);
////                    webView.loadUrl("http://t.kaozhibao.com" + source);
////                    webView.setWebViewClient(new WebViewClient(){
////                        @Override
////                        public void onPageFinished(WebView view, String url) {
////                            super.onPageFinished(view, url);
////                            view.buildDrawingCache();
////                            Bitmap bitmap = view.getDrawingCache();
////                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
////
////                            String picName = Base64.encodeBytes(url.replace("http://t.kaozhibao.com","").getBytes());
////                            File file = new File(Environment.getExternalStorageDirectory(), picName);
////                            FileOutputStream out= null;
////                            try {
////                                out = new FileOutputStream(file);
////                                out.write(baos.toByteArray(),0,baos.size());
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                            }finally {
////                                if (out != null) {
////                                    try {
////                                        out.close();
////                                    } catch (IOException e) {
////                                        e.printStackTrace();
////                                    }
////                                }
////
////                            }
////
////                            setText(Html.fromHtml(mText, new AnswerHttpImageGeter(), null));
////
////                        }
////                    });
////                    return drawable;
////                }else{
////
////
////                }
//
//
//// 不存在即开启异步任务加载网络图片
//                AsyncLoadNetworkPic networkPic = new AsyncLoadNetworkPic();
//                networkPic.execute(source);
//            }
//            return drawable;
//        }
//    }
//
//
//    /**
//     * 加载网络图片异步类
//     *
//     * @author Susie
//     */
//    private final class AsyncLoadNetworkPic extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            // 加载网络图片
//            loadNetPic(params);
//            return params[0];
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            // 当执行完成后再次为其设置一次
//            //问题的
//            setText(Html.fromHtml(mText, new AnswerHttpImageGeter(), null));
//        }
//
//        /**
//         * 加载网络图片
//         */
//        private void loadNetPic(String... params) {
//            String path = params[0];
//            String picName = Base64.encodeBytes(path.getBytes());
//
////            File file = new File(Environment.getExternalStorageDirectory(), picName);
//            File file = new File(FileUtil.getCachePath(), picName);
//
//            InputStream in = null;
//
//            FileOutputStream out = null;
//
//            try {
//                URL url;
//                if(path.startsWith("http")){
//                    url = new URL(path);
//                }else{
//                    url = new URL("http://t.kaozhibao.com" + path);
//                }
//
//                HttpURLConnection connUrl = (HttpURLConnection) url.openConnection();
//
//                connUrl.setConnectTimeout(5000);
//
//                connUrl.setRequestMethod("GET");
//
//                if (connUrl.getResponseCode() == 200) {
//
//                    in = connUrl.getInputStream();
//
//                    out = new FileOutputStream(file);
//
//                    byte[] buffer = new byte[1024];
//
//                    int len;
//
//                    while ((len = in.read(buffer)) != -1) {
//                        out.write(buffer, 0, len);
//                    }
//                } else {
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//
//                if (in != null) {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (out != null) {
//                    try {
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//
//}
//
