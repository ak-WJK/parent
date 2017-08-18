package com.kzb.parents.diagnose.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/3/24.
 */

public class DiagNoseResponse extends XBaseResponse {


    public DiagNoseResponse(String name, int openType) {
        this.name = name;
        this.openType = openType;
    }

    private String name;
    private int openType;//0表示关闭，1表示展开

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
    }
}
