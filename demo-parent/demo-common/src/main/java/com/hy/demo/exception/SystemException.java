package com.hy.demo.exception; /**
 * Description：
 * yao create 2023/6/8 17:59
 **/

import com.hy.demo.common.IErrorCode;
import com.hy.demo.common.ResultCode;

/**
 *  @ClassName SystemException
 *  description:
 *  yao create 2023年06月08日
 *  version: 1.0
 */
public class SystemException extends RuntimeException implements IErrorCode {
    private int code;

    private String msg;

    public SystemException() {
        this.code = ResultCode.ERROR.getCode();
        this.msg = ResultCode.ERROR.getMessage();
    }

    public SystemException(String message) {
        this.code =ResultCode.ERROR.getCode();
        this.msg = message;
    }

    public SystemException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
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
