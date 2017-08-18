package com.kzb.parents.main.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/5/10.
 */

public class LunBoResponse extends XBaseResponse {


    private List<LunBoModel> content;

    public List<LunBoModel> getContent() {
        return content;
    }

    public void setContent(List<LunBoModel> content) {
        this.content = content;
    }

    public static class LunBoModel{
        private String id;
        private String path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
