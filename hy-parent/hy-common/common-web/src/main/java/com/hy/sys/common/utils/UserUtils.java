package com.hy.sys.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.dto.UserJwtDto;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * @ClassName UserUtils
 * description: 用户相关工具类
 * yao create 2023年08月15日
 * version: 1.0
 */
public class UserUtils {
    /**
     * 获取从网关过滤过的用户信息
     * 通过 user 头
     * @param request
     * @return
     */
    public static UserJwtDto getUser(HttpServletRequest request){
        String token = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(token)){
            return null;
        }
        return JSONUtil.toBean(token, UserJwtDto.class);
    }

    /**
     * 获取从网关过滤过的用户信息
     * 通过 Authorization 头
     * @param request
     * @return
     */
    public static UserJwtDto getUserInfo(HttpServletRequest request){
        String jwt = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if(StrUtil.isEmpty(jwt)){
            return null;
        }
        String realToken = jwt.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        JSONObject payloads = JWTUtil.parseToken(realToken).getPayloads();
        return JSONUtil.toBean(payloads,UserJwtDto.class);
    }
}
