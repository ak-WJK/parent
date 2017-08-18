package com.kzb.parents.view.chart.bean;

/********************
 * 作者：malus
 * 日期：16/11/25
 * 时间：下午2:42
 * 注释：
 ********************/
public class Rate {
    public float rate;
    public int color;
    public String title;
    public String label;

    public Rate(float rate, int color, String title, String label) {
        this.rate = rate;
        this.color = color;
        this.title = title;
        this.label = label;
    }
}
