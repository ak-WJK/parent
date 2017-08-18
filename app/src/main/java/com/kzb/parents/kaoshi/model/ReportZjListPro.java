package com.kzb.parents.kaoshi.model;

import com.kzb.parents.base.XBaseResponse;
import com.kzb.parents.report.model.ReportZjListItem;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class ReportZjListPro extends XBaseResponse {
    private List<ReportZjListItem> content;

    public List<ReportZjListItem> getContent() {
        return content;
    }

    public void setContent(List<ReportZjListItem> content) {
        this.content = content;
    }
}
