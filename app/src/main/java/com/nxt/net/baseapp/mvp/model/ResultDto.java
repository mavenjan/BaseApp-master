package com.nxt.net.baseapp.mvp.model;import java.io.Serializable;/** * Created by Jan Maven on 2017/6/6. * Email:cyjiang_11@163.com * Description: */public class ResultDto implements Serializable {    /**     * code : 1     * msg : 添加成功！     */    private int code;    private String msg;    public int getCode() {        return code;    }    public void setCode(int code) {        this.code = code;    }    public String getMsg() {        return msg;    }    public void setMsg(String msg) {        this.msg = msg;    }}