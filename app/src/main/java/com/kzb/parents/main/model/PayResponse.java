package com.kzb.parents.main.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/7/19.
 */

public class PayResponse extends XBaseResponse {

    private PayModel content;

    public PayModel getContent() {
        return content;
    }

    public void setContent(PayModel content) {
        this.content = content;
    }

    public static class PayModel {
        private String status;
        private String timeout;
        private String good_id;

        public String getGood_id() {
            return good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimeout() {
            return timeout;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        @Override
        public String toString() {
            return "PayModel{" +
                    "status='" + status + '\'' +
                    ", timeout='" + timeout + '\'' +
                    ", good_id='" + good_id + '\'' +
                    '}';
        }
    }

}
