package com.kzb.parents.course.model;


import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 16/12/6.
 */

public class CourseResponse extends XBaseResponse {


    private List<ZhangModel> content;

    public List<ZhangModel> getContent() {
        return content;
    }

    public void setContent(List<ZhangModel> content) {
        this.content = content;
    }

    public static class ZhangModel{
        private String id;
        private String name;
        private String count;
        private List<JieModel> jie;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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

        public List<JieModel> getJie() {
            return jie;
        }

        public void setJie(List<JieModel> jie) {
            this.jie = jie;
        }

        @Override
        public String toString() {
            return "zhangModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", jie=" + jie +
                    '}';
        }
    }

    public static class JieModel{

        private String id;
        private String name;
        private List<KnowledgeModel> knowledges;
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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

        public List<KnowledgeModel> getKnowledges() {
            return knowledges;
        }

        public void setKnowledges(List<KnowledgeModel> knowledges) {
            this.knowledges = knowledges;
        }

        @Override
        public String toString() {
            return "JieModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", knowledges=" + knowledges +
                    '}';
        }
    }


    public static class KnowledgeModel{
        private String kid;
        private String kpoint;
        private String count;
        private String can;
        private String importance;


        public String getImportance() {
            return importance;
        }

        public void setImportance(String importance) {
            this.importance = importance;
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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCan() {
            return can;
        }

        public void setCan(String can) {
            this.can = can;
        }

        @Override
        public String toString() {
            return "KnowledgeModel{" +
                    "kid='" + kid + '\'' +
                    ", kpoint='" + kpoint + '\'' +
                    ", count='" + count + '\'' +
                    ", can='" + can + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "content=" + content +
                '}';
    }
}
