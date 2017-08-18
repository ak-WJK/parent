package com.kzb.parents.exam.bean;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/6/4.
 */

public class SubmitExam extends XBaseResponse {
    private SubmitContent content;

    public SubmitContent getContent() {
        return content;
    }

    public void setContent(SubmitContent content) {
        this.content = content;
    }
}
