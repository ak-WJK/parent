package com.kzb.parents.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.kzb.baselibrary.log.Log;
import com.kzb.parents.util.DensityUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;


/********************
 * 作者：malus
 * 日期：16/12/6
 * 时间：下午8:41
 * 注释：使用html的textView
 ********************/


public class QuesWebViewSix extends WebView {
    double mHeight;
    private float mRate = 0.35f;
    public QuesWebViewSix(Context context) {
        super(context);
    }

    public QuesWebViewSix(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundColor(Color.parseColor("#0000ff"));
    }

    public QuesWebViewSix(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void loadData(String data) {
//        data = ansData(data);
        data = getNewContent(data);

        data = data.replaceAll("<br>", "");
        data = data.replaceAll("\\r\\n", "");
        data = data.replaceAll("<p>", "");
        data = data.replaceAll("</p>", "");
        data = data.replaceAll("<br>", "");
        data = data.replaceAll("</br>", "");
        data = data.replaceAll("<div>", "");
        data = data.replaceAll("</div>", "");

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


    public static String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);


            Elements bodyElements=doc.getElementsByTag("body");

            for (Element element : bodyElements) {
                element.attr("style=\"width: 100%;height:100%;overflow: hidden\"");
            }

            Elements elements=doc.getElementsByTag("img");

            for (Element element : elements) {
                element.removeAttr("style");
                element.attr("width","110%").attr("height","auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

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


            ans = ans.replace(style,"width:150%;");
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


        ans = "<meta name=\"viewport\" content=\"width=device-width\">\n" +
                "<body style=\"width: 100%;height:100%;overflow: hidden\"><img style=\"width:150%;\" border=\"0\" src=\"/Public/ewebeditor/uploadfile/20170330164740435.png\">\n" +
                "</body>";


//        ans = "<meta name=\"viewport\" content=\"width=device-width\" ><span style='font-size:15pt;line-height:20pt'><img src=\"http://t.kaozhibao.com/Public/ewebeditor/uploadfile/20170523140033578.jpg\" style=\"width:100%;\" border=\"0\"></span>";

//        Log.e("kaoshi","mheight"+mHeight);
        return ans;
    }


}

