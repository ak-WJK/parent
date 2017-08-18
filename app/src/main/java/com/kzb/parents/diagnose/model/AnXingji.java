package com.kzb.parents.diagnose.model;


import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/********************
 * 作者：malus
 * 日期：17/1/10
 * 时间：下午9:24
 * 注释：
 ********************/
public class AnXingji extends XBaseResponse {
    private List<AnXingjiContent> content;

    public List<AnXingjiContent> getContent() {
        return content;
    }

    public void setContent(List<AnXingjiContent> content) {
        this.content = content;
    }
}
