package com.kzb.parents.settwo.model;


import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 16/11/30.
 */

public class YearResponse extends XBaseResponse {

    private List<YearModel> content;

    public List<YearModel> getContent() {
        return content;
    }

    public void setContent(List<YearModel> content) {
        this.content = content;
    }

    public static class YearModel{

        private String id;
        private String name;
        private List<GradeModel> grade;

        public List<GradeModel> getGrade() {
            return grade;
        }

        public void setGrade(List<GradeModel> grade) {
            this.grade = grade;
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

        @Override
        public String toString() {
            return "YearModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", grade=" + grade +
                    '}';
        }
    }

    public static class GradeModel{
        private String id;
        private String name;

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

        @Override
        public String toString() {
            return "gradeModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "YearResponse{" +
                "content=" + content +
                '}';
    }
}
