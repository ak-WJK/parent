package com.kzb.parents.diagnose.model;

/**
 * Created by wanghaofei on 17/1/20.
 */

public class QuestionModel {

    private int type;
    private String name;


    private String test_id;
    private String wrong_count;
    private String right_count;
    private String one;
    private String two;
    private String thr;


    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThr() {
        return thr;
    }

    public void setThr(String thr) {
        this.thr = thr;
    }

    public String getRight_count() {
        return right_count;
    }

    public void setRight_count(String right_count) {
        this.right_count = right_count;
    }

    public String getWrong_count() {
        return wrong_count;
    }

    public void setWrong_count(String wrong_count) {
        this.wrong_count = wrong_count;
    }

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
                ", wrong_count='" + wrong_count + '\'' +
                ", right_count='" + right_count + '\'' +
                ", one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", thr='" + thr + '\'' +
                '}';
    }
}
