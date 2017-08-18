package com.kzb.parents.main.model;

import com.kzb.parents.base.XBaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaofei on 17/7/8.
 */

public class VipResponse extends XBaseResponse implements Serializable {


    private List<MoneyModel> content;

    public List<MoneyModel> getContent() {
        return content;
    }

    public void setContent(List<MoneyModel> content) {
        this.content = content;
    }

    public static class MoneyModel implements Serializable{
        private String name;
        private String id;
        private String price;
        private String content;
        private String img;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
