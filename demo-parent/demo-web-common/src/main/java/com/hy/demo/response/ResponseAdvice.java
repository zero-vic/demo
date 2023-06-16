package com.hy.demo.response;

import cn.hutool.json.JSONUtil;
import com.hy.demo.common.CommonPage;
import com.hy.demo.common.CommonResult;
import com.hy.demo.exception.ExceptionResponse;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 *  @ClassName ResponseAdvice
 *  description:
 *  yao create 2023年06月07日
 *  version: 1.0
 */
@RestControllerAdvice(basePackages = "com.hy.demo")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    /**
     * 判断哪些需要拦截
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 返回结果包装
     *
     * @param body                  controller返回的数据
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof CommonResult) {
            return body;
        }

        if (body instanceof Boolean) {
            boolean result = (boolean) body;
            return new BaseResponse<Boolean>(result);
        }

        if (body instanceof CommonPage) {
            return new BaseResponse<>(body);
        }



        //字符串要特殊处理
        if(body instanceof String){
            BaseResponse<Object> result = new BaseResponse<>(body);
            try {

                String res = JSONUtil.toJsonStr(result);
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                response.getBody().write(res.getBytes());
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new BaseResponse<>(body);
    }
}
