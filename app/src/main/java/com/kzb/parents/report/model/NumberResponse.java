package com.kzb.parents.report.model;

import com.kzb.parents.base.XBaseResponse;

/**
 * Created by wanghaofei on 17/5/11.
 */

public class NumberResponse extends XBaseResponse {

    private NumberModel content;

    public NumberModel getContent() {
        return content;
    }

    public void setContent(NumberModel content) {
        this.content = content;
    }

    public static class NumberModel{
        private String qunake;
        private String zhangjie;
        private String zhenti;
        private String jiaoshi;

        public String getQunake() {
            return qunake;
        }

        public void setQunake(String qunake) {
            this.qunake = qunake;
        }

        public String getZhangjie() {
            return zhangjie;
        }

        public void setZhangjie(String zhangjie) {
            this.zhangjie = zhangjie;
        }

        public String getZhenti() {
            return zhenti;
        }

        public void setZhenti(String zhenti) {
            this.zhenti = zhenti;
        }

        public String getJiaoshi() {
            return jiaoshi;
        }

        public void setJiaoshi(String jiaoshi) {
            this.jiaoshi = jiaoshi;
        }
    }

}
