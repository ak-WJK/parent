package com.kzb.parents.exam.bean;

/**
 * Created by wanghaofei on 17/6/4.
 */

public class SubmitContent {
    private String test_id;
    private String all;
    private String done;
    private String allper;
    private String thisper;

    public String getAllper() {
        return allper;
    }

    public void setAllper(String allper) {
        this.allper = allper;
    }

    public String getThisper() {
        return thisper;
    }

    public void setThisper(String thisper) {
        this.thisper = thisper;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }
}
