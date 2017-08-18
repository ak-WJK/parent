package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class AreaResponse extends XBaseResponse {

    private List<AreaModel> content;

    public List<AreaModel> getContent() {
        return content;
    }

    public void setContent(List<AreaModel> content) {
        this.content = content;
    }

    public static class AreaModel{
        private String id;
        private String distinctname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDistinctname() {
            return distinctname;
        }

        public void setDistinctname(String distinctname) {
            this.distinctname = distinctname;
        }
    }

}
