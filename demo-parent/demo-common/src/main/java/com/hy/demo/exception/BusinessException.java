package com.hy.demo.exception;


import com.hy.demo.common.IErrorCode;
import com.hy.demo.common.ResultCode;

/**
 *  @ClassName BusinessException
 *  description: 业务处理异常
 *  yao create 2023年06月08日
 *  version: 1.0
 */
public class BusinessException extends RuntimeException  {
    private int code;

    private String msg;

    public BusinessException() {
        this.code = ResultCode.FAILED.getCode();
        this.msg = ResultCode.FAILED.getMessage();
    }

    public BusinessException(String message) {
        this.code =ResultCode.FAILED.getCode();
        this.msg = message;
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }



    public int getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
