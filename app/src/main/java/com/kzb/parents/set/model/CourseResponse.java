package com.kzb.parents.set.model;

import com.kzb.parents.base.XBaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaofei on 17/2/28.
 */

public class CourseResponse extends XBaseResponse {

    private List<CourseModel> content;

    public List<CourseModel> getContent() {
        return content;
    }

    public void setContent(List<CourseModel> content) {
        this.content = content;
    }

    public static class CourseModel implements Serializable {

        private String id;
        private String name;
        private String year;
        private String volume;
        private int type;//0表示未选中,1表示选中
        private String img;
        private String isopen;//1开通2未开通

        public String getIsopen() {
            return isopen;
        }

        public void setIsopen(String isopen) {
            this.isopen = isopen;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        @Override
        public String toString() {
            return "CourseModel{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", year='" + year + '\'' +
                    ", volume='" + volume + '\'' +
                    ", type=" + type +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "content=" + content +
                '}';
    }


}
