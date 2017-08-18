package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class SchoolResponse extends XBaseResponse {

    private List<SchoolModel> content;

    public List<SchoolModel> getContent() {
        return content;
    }

    public void setContent(List<SchoolModel> content) {
        this.content = content;
    }

    public static class SchoolModel{
        private String id;
        private String schoolname;

        private int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }
    }

}
