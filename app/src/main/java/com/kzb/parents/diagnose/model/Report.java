package com.kzb.parents.diagnose.model;


import com.kzb.parents.base.XBaseResponse;

/********************
 * 作者：malus
 * 日期：16/12/3
 * 时间：下午11:11
 * 注释：
 ********************/
public class Report extends XBaseResponse {
    public ReportContent content;

    public ReportContent getContent() {
        return content;
    }

    public void setContent(ReportContent content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Report{" +
                "content=" + content +
                '}';
    }
}
