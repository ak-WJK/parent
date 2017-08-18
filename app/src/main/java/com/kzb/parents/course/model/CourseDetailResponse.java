package com.kzb.parents.course.model;


import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 16/12/17.
 */

public class CourseDetailResponse extends XBaseResponse {



    private CourseDetailModel content;

    public CourseDetailModel getContent() {
        return content;
    }

    public void setContent(CourseDetailModel content) {
        this.content = content;
    }

    public static class CourseDetailModel{
        private String kid;
        private String kpoint;
        private String importance;
        private String explain;
        private String times;
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
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

        public String getImportance() {
            return importance;
        }

        public void setImportance(String importance) {
            this.importance = importance;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        @Override
        public String toString() {
            return "CourseDetailModel{" +
                    "kid='" + kid + '\'' +
                    ", kpoint='" + kpoint + '\'' +
                    ", importance='" + importance + '\'' +
                    ", explain='" + explain + '\'' +
                    ", times='" + times + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "CourseDetailResponse{" +
                "content=" + content +
                '}';
    }
}
