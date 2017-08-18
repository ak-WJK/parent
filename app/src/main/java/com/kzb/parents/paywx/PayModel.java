package com.kzb.parents.paywx;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/7/12.
 */

public class PayModel extends XBaseResponse {

    private PModel content;


    public PModel getContent() {
        return content;
    }

    public void setContent(PModel content) {
        this.content = content;
    }

    public static class PModel{
        private String appid;
        private String noncestr;
        private String pack;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String success;


        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPack() {
            return pack;
        }

        public void setPack(String pack) {
            this.pack = pack;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        @Override
        public String toString() {
            return "PModel{" +
                    "appid='" + appid + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", pack='" + pack + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", sign='" + sign + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", success='" + success + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "PayModel{" +
                "content=" + content +
                '}';
    }
}
