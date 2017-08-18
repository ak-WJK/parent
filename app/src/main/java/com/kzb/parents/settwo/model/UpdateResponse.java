package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class UpdateResponse extends XBaseResponse {


    private UpdateModel content;

    public UpdateModel getContent() {
        return content;
    }

    public void setContent(UpdateModel content) {
        this.content = content;
    }

    public static class UpdateModel{
        private String versioncode;
        private String versionname;
        private String content;
        private String url;
        private String required;
        private String timer;
        private String filesize;

        public String getVersioncode() {
            return versioncode;
        }

        public void setVersioncode(String versioncode) {
            this.versioncode = versioncode;
        }

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRequired() {
            return required;
        }

        public void setRequired(String required) {
            this.required = required;
        }

        public String getTimer() {
            return timer;
        }

        public void setTimer(String timer) {
            this.timer = timer;
        }

        public String getFilesize() {
            return filesize;
        }

        public void setFilesize(String filesize) {
            this.filesize = filesize;
        }

        @Override
        public String toString() {
            return "UpdateModel{" +
                    "versioncode='" + versioncode + '\'' +
                    ", versionname='" + versionname + '\'' +
                    ", content='" + content + '\'' +
                    ", url='" + url + '\'' +
                    ", required='" + required + '\'' +
                    ", timer='" + timer + '\'' +
                    ", filesize='" + filesize + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpdateResponse{" +
                "content=" + content +
                '}';
    }
}
