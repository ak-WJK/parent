package com.kzb.parents.payzfb.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/7/6.
 */

public class ZFBOrderResponse extends XBaseResponse {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ZFBOrderResponse{" +
                "content='" + content + '\'' +
                '}';
    }
}
