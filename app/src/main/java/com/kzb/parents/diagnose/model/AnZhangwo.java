package com.kzb.parents.diagnose.model;


import com.kzb.parents.base.XBaseResponse;

/********************
 * 作者：malus
 * 日期：17/1/10
 * 时间：下午9:24
 * 注释：
 ********************/
public class AnZhangwo extends XBaseResponse {
    private AnZhangwoContent content;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AnZhangwoContent getContent() {
        return content;
    }

    public void setContent(AnZhangwoContent content) {
        this.content = content;
    }
}
