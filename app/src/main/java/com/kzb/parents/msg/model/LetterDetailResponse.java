package com.kzb.parents.msg.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/7/17.
 */

public class LetterDetailResponse extends XBaseResponse {

    private LeDetailModel content;

    public LeDetailModel getContent() {
        return content;
    }

    public void setContent(LeDetailModel content) {
        this.content = content;
    }

    public static class LeDetailModel{
        private String title;
        private String content;
        private String addtime;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        @Override
        public String toString() {
            return "LeDetailModel{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", addtime='" + addtime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LetterDetailResponse{" +
                "content=" + content +
                '}';
    }
}
