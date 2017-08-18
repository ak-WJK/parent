package com.kzb.parents.login.model;

import com.kzb.parents.base.XBaseResponse;

import java.io.Serializable;

/**
 * Created by wanghaofei on 16/11/30.
 */

public class LoginResponse extends XBaseResponse {


    private LoginModel content;

    public LoginModel getContent() {
        return content;
    }

    public void setContent(LoginModel content) {
        this.content = content;
    }

    public static class LoginModel implements Serializable{

        private String uid;
        private String area_id;
        private String code;
        private String area;
        private String school_id;
        private String schoolname;
        private String schsystemid;
        private String schsystem;
        private String year_id;
        private String year;
        private String grade_id;
        private String grade;
        private String usergroup_id;
        private String usergroup;
        private String name;
        private String version_id;
        private String oauth_token;
        private String oauth_token_secret;
        private String infolock;
        private String subject;
        private String subject_id;
        private String sex;
        private String birthday;
        private String type;

        private String is_active;
        //is_active =  0为普通的没有完善，is_active=1为已完善，is_active = 2为外来的没完善

        private String cityname;
        private String city_id;
        private String volume_id;//学期
        private String distinctname;//区域名
        private String distinct_id;//区域ID

        private String status;

        //会员级别
        private String good_id;

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getIs_active() {
            return is_active;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getVolume_id() {
            return volume_id;
        }

        public void setVolume_id(String volume_id) {
            this.volume_id = volume_id;
        }

        public String getDistinctname() {
            return distinctname;
        }

        public void setDistinctname(String distinctname) {
            this.distinctname = distinctname;
        }

        public String getDistinct_id() {
            return distinct_id;
        }

        public void setDistinct_id(String distinct_id) {
            this.distinct_id = distinct_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getVersion_id() {
            return version_id;
        }

        public void setVersion_id(String version_id) {
            this.version_id = version_id;
        }

        public String getOauth_token() {
            return oauth_token;
        }

        public void setOauth_token(String oauth_token) {
            this.oauth_token = oauth_token;
        }

        public String getOauth_token_secret() {
            return oauth_token_secret;
        }

        public void setOauth_token_secret(String oauth_token_secret) {
            this.oauth_token_secret = oauth_token_secret;
        }

        public String getInfolock() {
            return infolock;
        }

        public void setInfolock(String infolock) {
            this.infolock = infolock;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSubject_id() {
            return subject_id;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSchool_id() {
            return school_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public String getSchoolname() {
            return schoolname;
        }

        public void setSchoolname(String schoolname) {
            this.schoolname = schoolname;
        }

        public String getSchsystemid() {
            return schsystemid;
        }

        public void setSchsystemid(String schsystemid) {
            this.schsystemid = schsystemid;
        }

        public String getSchsystem() {
            return schsystem;
        }

        public void setSchsystem(String schsystem) {
            this.schsystem = schsystem;
        }

        public String getYear_id() {
            return year_id;
        }

        public void setYear_id(String year_id) {
            this.year_id = year_id;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getUsergroup_id() {
            return usergroup_id;
        }

        public void setUsergroup_id(String usergroup_id) {
            this.usergroup_id = usergroup_id;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public void setUsergroup(String usergroup) {
            this.usergroup = usergroup;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "LoginModel{" +
                    "uid='" + uid + '\'' +
                    ", area_id='" + area_id + '\'' +
                    ", code='" + code + '\'' +
                    ", area='" + area + '\'' +
                    ", school_id='" + school_id + '\'' +
                    ", schoolname='" + schoolname + '\'' +
                    ", schsystemid='" + schsystemid + '\'' +
                    ", schsystem='" + schsystem + '\'' +
                    ", year_id='" + year_id + '\'' +
                    ", year='" + year + '\'' +
                    ", grade_id='" + grade_id + '\'' +
                    ", grade='" + grade + '\'' +
                    ", usergroup_id='" + usergroup_id + '\'' +
                    ", usergroup='" + usergroup + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "content=" + content +
                '}';
    }
}
