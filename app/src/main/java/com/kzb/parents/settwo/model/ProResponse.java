package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class ProResponse extends XBaseResponse {

    private List<ProModel> content;

    public List<ProModel> getContent() {
        return content;
    }

    public void setContent(List<ProModel> content) {
        this.content = content;
    }

    public static class ProModel{
        private String id;
        private String areaname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }
    }

}
