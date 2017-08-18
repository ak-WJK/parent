package com.kzb.parents.diagnose.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class Diagnose extends XBaseResponse {
    public List<DiagnoseItem> content;

    public List<DiagnoseItem> getContent() {
        return content;
    }

    public void setContent(List<DiagnoseItem> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Diagnose{" +
                "content=" + content +
                '}';
    }
}
