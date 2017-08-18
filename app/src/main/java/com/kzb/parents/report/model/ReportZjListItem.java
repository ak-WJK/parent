package com.kzb.parents.report.model;

import java.util.List;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class ReportZjListItem {
    private String id;
    private String name;
    private List<ReportListItem> test;
    private String testcount;

    private int openState;//0表示关闭，1展开

    public String getTestcount() {
        return testcount;
    }

    public void setTestcount(String testcount) {
        this.testcount = testcount;
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

    public List<ReportListItem> getTest() {
        return test;
    }

    public void setTest(List<ReportListItem> test) {
        this.test = test;
    }

    public int getOpenState() {
        return openState;
    }

    public void setOpenState(int openState) {
        this.openState = openState;
    }

    @Override
    public String toString() {
        return "ReportZjListItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", test=" + test +
                ", testcount='" + testcount + '\'' +
                '}';
    }
}
