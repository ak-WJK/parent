package com.kzb.parents.kaoshi.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/5/29.
 */

public class ZhenDuiXunLianResponse extends XBaseResponse {

    private List<XunLianModel> content;

    public List<XunLianModel> getContent() {
        return content;
    }

    public void setContent(List<XunLianModel> content) {
        this.content = content;
    }

    public static class XunLianModel{
        private String test_id;
        private String name;
        private String starttime;
        private String endtime;
        private String testnum;
        private String mytimes;

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
    }

}
