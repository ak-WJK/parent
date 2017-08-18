package com.kzb.parents.view.chart.bean;

import java.io.Serializable;

/********************
 * 作者：malus
 * 日期：16/12/16
 * 时间：下午4:55
 * 注释：
 ********************/
public class LineRankBean implements Serializable {
    public int rank;
    public String name;
    public String date;
    public String label;
    public float score;

    public LineRankBean() {
    }

    public LineRankBean(int rank, String name, String date, String label, float score) {
        this.rank = rank;
        this.name = name;
        this.date = date;
        this.label = label;
        this.score = score;
    }
}
