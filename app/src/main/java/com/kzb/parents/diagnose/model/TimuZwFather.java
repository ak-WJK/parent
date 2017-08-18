package com.kzb.parents.diagnose.model;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/12/5
 * 时间：下午10:04
 * 注释：
 ********************/
public class TimuZwFather {
    private String id;
    private String name;
    private List<TimuZwSon> knowledges = new ArrayList<>();
    private String gFather;

    public String getgFather() {
        return gFather;
    }

    public void setgFather(String gFather) {
        this.gFather = gFather;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimuZwSon> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<TimuZwSon> knowledges) {
        this.knowledges = knowledges;
    }


}
