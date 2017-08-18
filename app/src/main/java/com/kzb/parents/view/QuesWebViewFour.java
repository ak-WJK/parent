package com.kzb.parents.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.kzb.baselibrary.log.Log;
import com.kzb.parents.util.DensityUtil;


/********************
 * 作者：malus
 * 日期：16/12/6
 * 时间：下午8:41
 * 注释：使用html的textView
 ********************/


public class QuesWebViewFour extends WebView {
    double mHeight;
    private float mRate = 0.35f;
    public QuesWebViewFour(Context context) {
        super(context);
    }

    public QuesWebViewFour(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundColor(Color.parseColor("#0000ff"));
    }

    public QuesWebViewFour(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void loadData(String data) {
        data = ansData(data);

        Log.e("wwwww","data="+data);

        if (!data.contains("http://t.kaozhibao.com/Public/ewebeditor")) {
            data = data.replace("/Public/ewebeditor", "http://t.kaozhibao.com/Public/ewebeditor");
        }
        loadData(data,"text/html; charset=UTF-8", null);

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int expandSpec = MeasureSpec.makeMeasureSpec(DensityUtil.dip2px(getContext(), (float) (mHeight+15)), MeasureSpec.EXACTLY);
//        setMeasuredDimension(widthMeasureSpec,expandSpec);
//    }

    private String ansData(String ans) {

        String temp = "<meta name=\"viewport\" content=\"width=device-width\" >";

        ans = temp+ans;

//        int index = 0;
//        mHeight = 0;
//        int startIndex = ans.indexOf("\"", index);
//        int stopIndex = ans.indexOf("\"", startIndex + 1);
//        String style = ans.substring(startIndex + 1, stopIndex);
//
//        ans = ans.replace(style,"style=\"width:100%;\"");


        int index = 0;
        mHeight = 0;
        while ((index = ans.indexOf("style=", index)) > 0) {
            index++;
            int startIndex = ans.indexOf("\"", index);
            int stopIndex = ans.indexOf("\"", startIndex + 1);
            String style = ans.substring(startIndex + 1, stopIndex);


            if (style.contains("width") && style.contains("height")&&startIndex>0&&stopIndex>startIndex) {
//                String width = style.split("width")[1];
//                width = width.split(";")[0];
//                width = width.replaceAll(":", "");
//                width = width.replaceAll(" ", "");
//                width = width.replaceAll("px", "");
//
//                Log.e("kaoshi","width...guolv....."+width);
//
//                String height = style.split("height")[1];
//                height = height.split(";")[0];
//                height = height.replaceAll(":", "");
//                height = height.replaceAll(" ", "");
//                height = height.replaceAll("px", "");
//
//
//                try {
//                    double w = Double.parseDouble(width);
//                    double h = Double.parseDouble(height);
//
//                    Log.e("kaoshi","get:w="+w+";h="+h);
//
//                    w *= mRate;
//                    h *= mRate;
//
//                    Log.e("kaoshi","after:w="+w+";h="+h);

//                    ans = ans.replaceAll("width:"+width,"width:"+(int)w);
//                    ans = ans.replaceAll("height:"+height,"height:"+(int)h);
//
//                    ans = ans.replaceAll("width: "+width,"width:"+(int)w);
//                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);
//
//                    ans = ans.replaceAll("width :"+width,"width:"+(int)w);
//                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);


//                    ans = ans.replaceAll("width:"+width,"width:"+ (Application.WIDTH-150)/2);
//                    ans = ans.replaceAll("height:"+height,"height:"+(int)h);
//
//                    ans = ans.replaceAll("width: "+width,"width:"+(Application.WIDTH-150)/2);
//                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);
//
//                    ans = ans.replaceAll("width :"+width,"width:"+(Application.WIDTH-150)/2);
//                    ans = ans.replaceAll("height :"+height,"height:"+(int)h);



//                    ans = ans.replaceAll("width:"+width+"px","width:100%");
//                    ans = ans.replaceAll("height:"+height+"px","");
//
//                    ans = ans.replaceAll("width: "+width+"px","width:100%");
//                    ans = ans.replaceAll("height :"+height+"px","");
//
//                    ans = ans.replaceAll("width :"+width+"px","width:100%");
//                    ans = ans.replaceAll("height :"+height+"px","");




//                    mHeight += h;
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

            ans = ans.replace(style,"width:100%;");
            }

        }
        index = 0;
        while ((index = ans.indexOf("<br>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }
        index = 0;
        while ((index = ans.indexOf("<br/>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }
        index = 0;
        while ((index = ans.indexOf("<p>", index)) > 0) {
            index++;
            mHeight+=DensityUtil.dip2px(getContext(),20);
        }



        Log.e("kaoshi","after:ans="+ans);


//        ans = "<meta name=\"viewport\" content=\"width=device-width\" ><span style='font-size:15pt;line-height:20pt'><img src=\"http://t.kaozhibao.com/Public/ewebeditor/uploadfile/20170523140033578.jpg\" style=\"width:100%;\" border=\"0\"></span>";

//        Log.e("kaoshi","mheight"+mHeight);
        return ans;
    }


}

