package com.kzb.parents.report.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class ReportListPro  extends XBaseResponse {
    private List<ReportListItem> content;

    public List<ReportListItem> getContent() {
        return content;
    }

    public void setContent(List<ReportListItem> content) {
        this.content = content;
    }
}
