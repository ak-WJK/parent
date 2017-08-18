package com.kzb.parents.diagnose.model;

import java.util.List;

/********************
 * 作者：malus
 * 日期：17/1/10
 * 时间：下午9:24
 * 注释：
 ********************/
public class AnZhangwoContent{
    List<TimuZwSon> get;
    List<TimuZwSon> un;
    List<TimuZwSon> unget;

    public List<TimuZwSon> getGet() {
        return get;
    }

    public void setGet(List<TimuZwSon> get) {
        this.get = get;
    }

    public List<TimuZwSon> getUn() {
        return un;
    }

    public void setUn(List<TimuZwSon> un) {
        this.un = un;
    }

    public List<TimuZwSon> getUnget() {
        return unget;
    }

    public void setUnget(List<TimuZwSon> unget) {
        this.unget = unget;
    }
}
