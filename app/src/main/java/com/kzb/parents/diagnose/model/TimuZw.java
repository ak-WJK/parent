package com.kzb.parents.diagnose.model;


import com.kzb.parents.base.XBaseResponse;
import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/12/5
 * 时间：下午8:56
 * 注释：
 ********************/
public class TimuZw extends XBaseResponse {
    private List<TimuZwGFather> content = new ArrayList<>();

    public List<TimuZwGFather> getContent() {
        return content;
    }

    public void setContent(List<TimuZwGFather> content) {
        this.content = content;
    }
}
