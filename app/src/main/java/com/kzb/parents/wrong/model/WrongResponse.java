package com.kzb.parents.wrong.model;


import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 16/12/7.
 */

public class WrongResponse extends XBaseResponse {


    private List<WZhangModel> content;

    public List<WZhangModel> getContent() {
        return content;
    }

    public void setContent(List<WZhangModel> content) {
        this.content = content;
    }

    public static class WZhangModel{
        private String id;
        private String name;
        private List<WJieModel> jie;
        private String count;
        private int openState;//0关闭，1展开

        public int getOpenState() {
            return openState;
        }

        public void setOpenState(int openState) {
            this.openState = openState;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<WJieModel> getJie() {
            return jie;
        }

        public void setJie(List<WJieModel> jie) {
            this.jie = jie;
        }

        @Override
        public String toString() {
            return "WZhangModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", jie=" + jie +
                    '}';
        }
    }

    public static class WJieModel{

        private String id;
        private String name;
        private String count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "WJieModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", count='" + count + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "WrongResponse{" +
                "content=" + content +
                '}';
    }
}
