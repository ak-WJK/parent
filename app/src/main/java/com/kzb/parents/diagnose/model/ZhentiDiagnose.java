package com.kzb.parents.diagnose.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/6.
 */

public class ZhentiDiagnose extends XBaseResponse {
    public List<ZhentiDiagnoseItem> content;

    public List<ZhentiDiagnoseItem> getContent() {
        return content;
    }

    public void setContent(List<ZhentiDiagnoseItem> content) {
        this.content = content;
    }
}
