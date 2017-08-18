package com.kzb.parents.diagnose.model;

/**
 * Created by wanghaofei on 17/1/20.
 */

public class QuestionModel {

    private int type;
    private String name;
    private String test_id;

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", test_id='" + test_id + '\'' +
                '}';
    }
}
