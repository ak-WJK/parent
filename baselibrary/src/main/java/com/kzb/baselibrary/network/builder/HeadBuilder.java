package com.kzb.baselibrary.network.builder;


import com.kzb.baselibrary.network.OkHttpUtils;
import com.kzb.baselibrary.network.request.OtherRequest;
import com.kzb.baselibrary.network.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
