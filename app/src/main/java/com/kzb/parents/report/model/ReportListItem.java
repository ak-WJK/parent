package com.kzb.parents.report.model;

/**
 * Created by wanghaofei on 17/4/6.
 */

public class ReportListItem {
    private String test_id;
    private String code;
    private String score;
    private String addtime;
    private String usedtime;
    private String name;
    private String rank;

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(String usedtime) {
        this.usedtime = usedtime;
    }

    @Override
    public String toString() {
        return "ReportListItem{" +
                "test_id='" + test_id + '\'' +
                ", code='" + code + '\'' +
                ", score='" + score + '\'' +
                ", addtime='" + addtime + '\'' +
                ", usedtime='" + usedtime + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }
}
