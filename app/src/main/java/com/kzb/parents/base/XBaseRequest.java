package com.kzb.parents.base;

import java.util.Map;
/**
 * Created by wanghaofei on 16/10/11.
 * 联网请求数据时的一些基本参数
 */

public class XBaseRequest {


    private String dk;//设备信息
    private String token;//用户标识
    private String url;//请求地址
    public boolean isShowDialog = false;//是否显示转圈
    private int pagenumber;
    private int pagesize;


    private Map<String,String> requestParams;


    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public XBaseRequest(){
        this.token = null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
