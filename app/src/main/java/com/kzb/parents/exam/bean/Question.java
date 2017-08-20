package com.kzb.parents.exam.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class Question implements Serializable {
    private String question_id;
    private String question;
    private String type_id;
    private String question_type;
    private String explain;//强化提高才有的
    private String kids;//强化提高才有的
    private String myanswer;
    private String istrue;
    private boolean isDone;//是否解答
    public List<Answer> answers = new ArrayList<>();
    private List<ExplainContent.Knowledges> knowledges;

    public List<ExplainContent.Knowledges> getKnowledges() {
        return knowledges;
    }

    public String getIstrue() {
        return istrue;
    }

    public void setIstrue(String istrue) {
        this.istrue = istrue;
    }

    public void setKnowledges(List<ExplainContent.Knowledges> knowledges) {
        this.knowledges = knowledges;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getMyanswer() {
        return myanswer;
    }

    public void setMyanswer(String myanswer) {
        this.myanswer = myanswer;
    }




    @Override
    public String toString() {
        return "Question{" +
                "question_id='" + question_id + '\'' +
                ", question='" + question + '\'' +
                ", type_id='" + type_id + '\'' +
                ", question_type='" + question_type + '\'' +
                ", explain='" + explain + '\'' +
                ", kids='" + kids + '\'' +
                ", myanswer='" + myanswer + '\'' +
                ", isDone=" + isDone +
                ", answers=" + answers +
                '}';
    }
}
