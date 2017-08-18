package com.kzb.parents.kaoshi.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/1.
 */

public class KSZhangJieResponse extends XBaseResponse{

    private List<ZhangJieModel> content;

    public List<ZhangJieModel> getContent() {
        return content;
    }

    public void setContent(List<ZhangJieModel> content) {
        this.content = content;
    }

    public static class ZhangJieModel{
        private String test_id;
        private String name;
        private String starttime;
        private String endtime;
        private String testnum;
        private String mytimes;

        private String usedtime;
        private String addtime;
        private String score;

        public String getTestnum() {
            return testnum;
        }

        public void setTestnum(String testnum) {
            this.testnum = testnum;
        }

        public String getMytimes() {
            return mytimes;
        }

        public void setMytimes(String mytimes) {
            this.mytimes = mytimes;
        }

        public String getUsedtime() {
            return usedtime;
        }

        public void setUsedtime(String usedtime) {
            this.usedtime = usedtime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getTest_id() {
            return test_id;
        }

        public void setTest_id(String test_id) {
            this.test_id = test_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }

}
