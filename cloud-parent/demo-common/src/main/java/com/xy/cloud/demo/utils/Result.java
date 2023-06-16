package com.xy.cloud.demo.utils;



/**
 * 返回结果通用封装
 */

public class Result {
    // 返回状态
    private int status;
    // 状态描述
    private String desc;
    // 返回数据
    private Object data;

    private String token;

    private String refreshToken;

    public Result(int status, String desc, Object data, String token, String refreshToken) {
        this.status = status;
        this.desc = desc;
        this.data = data;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}