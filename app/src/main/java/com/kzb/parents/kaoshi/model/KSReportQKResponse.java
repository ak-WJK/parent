package com.kzb.parents.kaoshi.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/2.
 */

public class KSReportQKResponse extends XBaseResponse {

    private List<ReportQKModel> content;

    public List<ReportQKModel> getContent() {
        return content;
    }

    public void setContent(List<ReportQKModel> content) {
        this.content = content;
    }

    public static class ReportQKModel{
        private String test_id;
        private String code;
        private String score;
        private String addtime;
        private String usedtime;
        private String name;
        private String rank;

        public String getTest_id() {
            return test_id;
        }

        public void setTest_id(String test_id) {
            this.test_id = test_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUsedtime() {
            return usedtime;
        }

        public void setUsedtime(String usedtime) {
            this.usedtime = usedtime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }
    }
}
