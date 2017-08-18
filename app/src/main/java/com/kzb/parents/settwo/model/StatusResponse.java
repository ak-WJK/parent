package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class StatusResponse extends XBaseResponse {


    private StatusModel content;

    public StatusModel getContent() {
        return content;
    }

    public void setContent(StatusModel content) {
        this.content = content;
    }

    public static class StatusModel{

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}

