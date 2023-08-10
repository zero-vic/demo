package com.hy.sys.common.exception;

import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * description: 全局异常处理类
 * yao create 2023年08月10日
 * version: 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 服务名
     */
    @Value("${spring.application.name}")
    private String serverName;

    /**
     * 微服务系统标识
     */
    private String errorSystem;

    @PostConstruct
    public void init() {
        this.errorSystem = new StringBuffer()
                .append(this.serverName)
                .append(": ").toString();
    }

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {

    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {

    }

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = {Exception.class})
    public CommonResult<String> handlerException(Exception exception, HttpServletRequest request) {
        log.error("请求路径uri={},系统内部出现异常:{}", request.getRequestURI(), exception.getMessage(),exception);

        return CommonResult.failed(ResultCode.ERROR);
    }

    /**
     * 非法请求异常
     */
    @ExceptionHandler(value = {
            HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            NoHandlerFoundException.class,
            MissingPathVariableException.class,
            HttpMessageNotReadableException.class
    })
    public CommonResult<String> handlerSpringAOPException(Exception exception) {
        log.error("非法请求异常:{}",exception.getMessage(),exception);
        return CommonResult.failed(ResultCode.ILLEGAL_REQUEST,exception.getMessage());
    }


    /**
     * 非法请求异常-参数类型不匹配
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public CommonResult<String> handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.error("参数类型不匹配:{}",exception.getMessage(),exception);
        return CommonResult.failed(ResultCode.PARAM_TYPE_MISMATCH);
    }


    /**
     * 非法请求异常-参数校验
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public CommonResult<String> handlerConstraintViolationException(ConstraintViolationException exception) {
        log.error("参数校验异常:{}",exception.getMessage(),exception);
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, exception.getMessage());
    }
    /**
     * 非法请求-参数校验 @RequestBody上使用@Valid 实体上使用@NotNull等，验证失败后抛出的异常是MethodArgumentNotValidException异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResult<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        //获取异常字段及对应的异常信息
        StringBuffer stringBuffer = new StringBuffer();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .map(t -> t.getField() + t.getDefaultMessage() + ";")
                .forEach(e -> stringBuffer.append(e));
        String errorMessage = stringBuffer.toString();
        log.error("参数校验异常:{}",errorMessage,methodArgumentNotValidException);
        return  CommonResult.failed(ResultCode.VALIDATE_FAILED, errorMessage);

    }



    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public CommonResult BindExceptionHandler(BindException exception) {
        //获取异常字段及对应的异常信息
        StringBuffer stringBuffer = new StringBuffer();
        exception.getBindingResult().getFieldErrors().stream()
                .map(t -> t.getField() + t.getDefaultMessage() + ";")
                .forEach(e -> stringBuffer.append(e));
        String errorMessage = stringBuffer.toString();
        log.error("参数校验异常:{}",errorMessage,exception);
        return  CommonResult.failed(ResultCode.VALIDATE_FAILED, errorMessage);
    }



    /**
     * 自定义业务异常-BusinessException
     */
    @ExceptionHandler(value = {BusinessException.class})
    public CommonResult<String> handlerCustomException(BusinessException exception) {
        String errorMessage = exception.getMsg();
        log.error("业务异常：{}",errorMessage,exception);
        return CommonResult.failed(exception.getCode(),errorMessage);

    }

    /**
     * 自定义系统异常-SystemException
     */
    @ExceptionHandler(value = {SystemException.class})
    public CommonResult<String> handlerCustomException(SystemException exception) {
        String errorMessage = exception.getMsg();
        log.error("系统异常：{}",errorMessage,exception);
        return CommonResult.failed(exception.getCode(),errorMessage);
    }
}
