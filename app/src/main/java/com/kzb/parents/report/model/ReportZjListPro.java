package com.kzb.parents.report.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class ReportZjListPro extends XBaseResponse {
    private List<ReportZjListItem> content;

    public List<ReportZjListItem> getContent() {
        return content;
    }

    public void setContent(List<ReportZjListItem> content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "ReportZjListPro{" +
                "content=" + content +
                '}';
    }
}
