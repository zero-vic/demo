package com.hy.sys.common.result;

/**
 *
 * @Description  枚举了一些常用API操作码
 * @Author yao
 * @Date 2023/5/25 12:28
 **/

public enum ResultCode implements IErrorCode {
    /**
     *操作成功
     **/
    SUCCESS(200, "操作成功"),
    FAILED(101, "操作失败"),

    ERROR(500,"系统错误"),
    /**
     * 参数错误
     */
    PARAM_ERROR(103, "参数错误"),

    /**
     * 参数错误-已存在
     */
    INVALID_PARAM_EXIST(104, "请求参数已存在"),

    /**
     * 参数错误
     */
    INVALID_PARAM_EMPTY(105, "请求参数为空"),

    /**
     * 参数错误
     */
    PARAM_TYPE_MISMATCH(106, "参数类型不匹配"),
    VALIDATE_FAILED(107, "参数检验失败"),
    ILLEGAL_REQUEST(108, "非法请求"),


    FILE_SIZE_LIMIT(134,"上传文件大小超出限制"),

    FILE_TYPE_ERROR(135,"上传文件类型不对"),

    FILE_FAILED(136,"文件上传失败"),

    CAPTCHA_ERROR(204, "验证码错误"),

    CAPTCHA_EXPIRE(205, "验证码过期"),

    USERNAME_PWD_ERROR(400,"用户名或密码错误"),
    UNAUTHORIZED(401, "暂未登录"),

    MISS_USER_INFO(402,"缺失用户信息"),
    FORBIDDEN(403, "没有相关权限"),
    CLIENT_AUTHENTICATION_FAILED(406,"客户认证异常"),

    TOKEN_INVALID(408,"token无效"),
    TOKEN_EXPIRED(407,"token已经过期")
    ;

    private int code;
    private String message;

    private ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}