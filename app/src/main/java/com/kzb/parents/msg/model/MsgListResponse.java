package com.kzb.parents.msg.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/7/17.
 */

public class MsgListResponse extends XBaseResponse {

    private List<MsgListModel> content;

    public List<MsgListModel> getContent() {
        return content;
    }

    public void setContent(List<MsgListModel> content) {
        this.content = content;
    }

    public static class MsgListModel{
        private String id;
        private String title;
        private String addtime;
        private String readcount;

        public String getReadcount() {
            return readcount;
        }

        public void setReadcount(String readcount) {
            this.readcount = readcount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        @Override
        public String toString() {
            return "LetterListModel{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", addtime='" + addtime + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "MsgListResponse{" +
                "content=" + content +
                '}';
    }
}
