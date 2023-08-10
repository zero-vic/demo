package com.hy.sys.gateway.component;

import cn.hutool.json.JSONUtil;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 *
 * @Description  自定义返回结果：没有登录或token过期时
 * @Author yao
 * @Date 2023/5/25 14:09     
 **/
@Component
public class RestAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin","*");
        response.getHeaders().set("Cache-Control","no-cache");
        String body = "";
        // 根据异常返回结果
        if (e.getCause() instanceof InsufficientAuthenticationException){
            body = JSONUtil.toJsonStr(CommonResult.unauthorized(e.getMessage()));
        }else if ( e.getCause() instanceof JwtValidationException) {
            body = JSONUtil.toJsonStr(CommonResult.failed(ResultCode.TOKEN_EXPIRED));
        }else {
            body = JSONUtil.toJsonStr(CommonResult.failed(ResultCode.ERROR));
        }
        DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}