package com.kzb.parents.settwo.model;

import com.kzb.parents.base.XBaseResponse;

import java.util.List;

/**
 * Created by wanghaofei on 17/6/12.
 */

public class CityResponse extends XBaseResponse {


    private List<CityModel> content;

    public List<CityModel> getContent() {
        return content;
    }

    public void setContent(List<CityModel> content) {
        this.content = content;
    }

    public static class CityModel {
        private String id;
        private String cityname;
        private String areaid;
        private String act;
        private String distinct;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getAct() {
            return act;
        }

        public void setAct(String act) {
            this.act = act;
        }

        public String getDistinct() {
            return distinct;
        }

        public void setDistinct(String distinct) {
            this.distinct = distinct;
        }
    }

}
