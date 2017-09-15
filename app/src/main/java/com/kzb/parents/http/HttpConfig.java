package com.kzb.parents.http;

import com.kzb.baselibrary.network.OkHttpUtils;
import com.kzb.baselibrary.network.callback.GenericsCallback;
import com.kzb.parents.base.XBaseRequest;
import com.kzb.parents.config.Config;
import com.kzb.parents.util.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghaofei on 16/11/29.
 */

public class HttpConfig {

    /**
     * @param request          联网请求的一些地址及参数
     * @param genericsCallback 是否请求成功状态的回调
     */


    public void doPostRequest(final XBaseRequest request, final GenericsCallback genericsCallback) {
        LogUtils.e("wang", "..here..");


        if (null == request.getUrl()) {
            //  logicBack.error("网络地址为空", Config.re_error);
            LogUtils.e("wang", "网络地址为空");
            return;
        }

        final String url = Config.JAVA_API_URL + request.getUrl();

        LogUtils.e("wang", "url=" + url);

        if (request.getRequestParams() == null) {


            try {
                OkHttpUtils
                        .post()//
                        .url(url)//
                        .addHeader("Content-Type", "text/xml")
                        .addHeader("Accept-Encoding", "gzip")
                        .build()//
                        .execute(genericsCallback);

            } catch (Exception e) {

            }
        } else

        {

            LogUtils.e("params", request.getRequestParams().toString());

            try {

                OkHttpUtils
                        .post()//
                        .params(request.getRequestParams())
                        .url(url)//
                        .addHeader("Content-Type", "text/xml")
                        .addHeader("Accept-Encoding", "gzip")
                        .build()//
                        .execute(genericsCallback);

            } catch (Exception e) {

            }
        }

    }

    public static XBaseRequest getBaseRequest(String url, JSONObject json) {
        XBaseRequest request = new XBaseRequest();
        request.setUrl(url);
        Map<String, String> map = new HashMap();
        map.put("info", json.toString());
        if (json != null) {
            LogUtils.e("request", json.toString());
        }
        request.setRequestParams(map);
        return request;
    }


    //上传头像
    public void uploadUserIcon(final XBaseRequest request, String uid, String name, String fileName, File file, final GenericsCallback genericsCallback) {
        LogUtils.e("wang", "..here..");


        if (null == request.getUrl()) {
            //  logicBack.error("网络地址为空", Config.re_error);
            LogUtils.e("wang", "网络地址为空");
            return;
        }

        final String url = Config.JAVA_API_URL + request.getUrl();

        LogUtils.e("wang", "url=" + url);

        if (request.getRequestParams() == null) {


            try {
                OkHttpUtils
                        .post()//
                        .url(url)//
                        .addHeader("Content-Type", "text/xml")
                        .addHeader("Accept-Encoding", "gip")
                        .addParams("uid", uid)
                        .addFile(name, fileName, file)
                        .build()//
                        .execute(genericsCallback);

            } catch (Exception e) {

            }
        } else

        {

            LogUtils.e("params", request.getRequestParams().toString());

            try {

                OkHttpUtils
                        .post()//
                        .params(request.getRequestParams())
                        .url(url)//
                        .addHeader("Content-Type", "text/xml")
                        .addHeader("Accept-Encoding", "gzip")
                        .addFile(name, fileName, file)
                        .addParams("uid", uid)
                        .build()//
                        .execute(genericsCallback);

            } catch (Exception e) {

            }
        }

    }
}
