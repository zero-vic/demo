package com.hy.sys.common.exception;

/**
 *
 * @Description 封装异常返回工具类
 * @Author yao
 * @Date 2023/5/29 14:58
 **/
public class ExceptionResponse{
    private String msg;
    public ExceptionResponse(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}