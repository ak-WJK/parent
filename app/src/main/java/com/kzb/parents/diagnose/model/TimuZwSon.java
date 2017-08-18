package com.kzb.parents.diagnose.model;

/********************
 * 作者：malus
 * 日期：16/12/5
 * 时间：下午10:04
 * 注释：
 ********************/
public class TimuZwSon {
    private String kid;
    private String kpoint;
    private int can;//0掌握1未掌握2未出
    private String importance;

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getKpoint() {
        return kpoint;
    }

    public void setKpoint(String kpoint) {
        this.kpoint = kpoint;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int can) {
        this.can = can;
    }
}
