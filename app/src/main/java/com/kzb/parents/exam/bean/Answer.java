package com.kzb.parents.exam.bean;

import java.io.Serializable;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class Answer implements Serializable {
    public String answer;
    public String istrue;
    public String answer_id;
    public boolean isCheck;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIstrue() {
        return istrue;
    }

    public void setIstrue(String istrue) {
        this.istrue = istrue;
    }

    public String getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(String answer_id) {
        this.answer_id = answer_id;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }


    @Override
    public String toString() {
        return "Answer{" +
                "answer='" + answer + '\'' +
                ", istrue='" + istrue + '\'' +
                ", answer_id='" + answer_id + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
