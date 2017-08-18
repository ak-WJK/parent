package com.kzb.parents.diagnose.model;

import java.util.List;

/********************
 * 作者：malus
 * 日期：17/1/10
 * 时间：下午9:24
 * 注释：
 ********************/
public class AnXingjiContent {
    private int importance;
    private List<TimuZwSon> con;

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public List<TimuZwSon> getCon() {
        return con;
    }

    public void setCon(List<TimuZwSon> con) {
        this.con = con;
    }
}
