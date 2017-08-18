package com.kzb.parents.wrong.model;

import java.util.List;

/**
 * Created by wanghaofei on 17/3/28.
 */

public class ExplainContent {
        private String question_id;
        private String question;
        private String kids;
        private String explain;
        private String myanswer;
        private String istrue;
        private String type_id;
        private List<Answer> answers;
        private List<Knowledges> knowledges;
        private boolean isJiexi;
        private String isright;

        public String getIsright() {
            return isright;
        }

        public void setIsright(String isright) {
            this.isright = isright;
        }

        public String getIstrue() {
            return istrue;
        }

        public void setIstrue(String istrue) {
            this.istrue = istrue;
        }

        public boolean isJiexi() {
            return isJiexi;
        }

        public void setJiexi(boolean jiexi) {
            isJiexi = jiexi;
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

        public String getKids() {
            return kids;
        }

        public void setKids(String kids) {
            this.kids = kids;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getMyanswer() {
            return myanswer;
        }

        public void setMyanswer(String myanswer) {
            this.myanswer = myanswer;
        }

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public List<Answer> getAnswers() {
            return answers;
        }

        public void setAnswers(List<Answer> answers) {
            this.answers = answers;
        }

        public List<Knowledges> getKnowledges() {
            return knowledges;
        }

        public void setKnowledges(List<Knowledges> knowledges) {
            this.knowledges = knowledges;
        }

public class Knowledges{
    private String kid;
    private String kpoint;
    private String difficulty;
    private String isget;

    public String getIsget() {
        return isget;
    }

    public void setIsget(String isget) {
        this.isget = isget;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getKpoint() {
        return kpoint;
    }

    public void setKpoint(String kpoint) {
        this.kpoint = kpoint;
    }
}

    @Override
    public String toString() {
        return "ExplainContent{" +
                "question_id='" + question_id + '\'' +
                ", question='" + question + '\'' +
                ", kids='" + kids + '\'' +
                ", explain='" + explain + '\'' +
                ", myanswer='" + myanswer + '\'' +
                ", istrue='" + istrue + '\'' +
                ", type_id='" + type_id + '\'' +
                ", answers=" + answers +
                ", knowledges=" + knowledges +
                ", isJiexi=" + isJiexi +
                ", isright='" + isright + '\'' +
                '}';
    }
}
