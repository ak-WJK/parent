package com.kzb.parents.diagnose.model;

import com.kzb.parents.base.XBaseResponse;
import com.kzb.parents.wrong.model.ExplainContent;

import java.util.List;

/**
 * Created by wanghaofei on 17/5/13.
 */

public class ExplainPro  extends XBaseResponse {
    private List<ExplainContent> content;

    public List<ExplainContent> getContent() {
        return content;
    }

    public void setContent(List<ExplainContent> content) {
        this.content = content;
    }
}
