package com.kzb.parents.diagnose.model;

import java.util.ArrayList;
import java.util.List;

/********************
 * 作者：malus
 * 日期：16/12/5
 * 时间：下午10:04
 * 注释：
 ********************/
public class TimuZwGFather {
    private String id;
    private String name;
    private List<TimuZwFather> jie = new ArrayList<>();

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

    public List<TimuZwFather> getJie() {
        return jie;
    }

    public void setJie(List<TimuZwFather> jie) {
        this.jie = jie;
    }
}
