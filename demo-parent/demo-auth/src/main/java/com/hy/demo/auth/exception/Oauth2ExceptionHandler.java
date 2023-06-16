package com.hy.demo.auth.exception;


import com.hy.demo.common.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @Description 全局处理Oauth2抛出的异常
 * @Author yao
 * @Date 2023/5/25 16:48
 **/
@ControllerAdvice
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public CommonResult handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getHttpErrorCode(), e.getMessage());
    }
}
