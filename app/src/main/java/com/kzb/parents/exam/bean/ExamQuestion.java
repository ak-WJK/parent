package com.kzb.parents.exam.bean;

import com.kzb.parents.base.XBaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class ExamQuestion extends XBaseResponse {
    private List<Question> content;
    public List<Question> getContent() {
        return content;
    }

    public void setContent(List<Question> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ExamQuestion{" +
                "content=" + content +
                '}';
    }

    public List<ExplainContent> getExplainContent(){
        List<ExplainContent> contents = new ArrayList<>();

        if (content != null) {
            for (Question question : content) {
                ExplainContent explain = new ExplainContent();
                explain.setQuestion_id(question.getQuestion_id());
                explain.setQuestion(question.getQuestion());
                explain.setKids(question.getKids());
                explain.setExplain(question.getExplain());
                explain.setMyanswer(question.getMyanswer());
                explain.setType_id(question.getType_id());
                explain.setKnowledges(question.getKnowledges());
                List<Answer> answers = new ArrayList<>();
                answers.addAll(question.getAnswers());
                explain.setAnswers(answers);
                explain.setJiexi(false);

                contents.add(explain);
            }
        }
        return contents;
    }
}
