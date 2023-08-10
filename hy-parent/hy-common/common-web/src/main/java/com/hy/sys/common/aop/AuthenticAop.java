package com.hy.sys.common.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hy.sys.common.annotation.AuthRequire;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.exception.BusinessException;
import com.hy.sys.common.result.ResultCode;
import com.hy.sys.service.SysmenuService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 *  @ClassName AuthenticAop
 *  description:
 *  yao create 2023年06月07日
 *  version: 1.0
 */
@Component
@Aspect
public class AuthenticAop {
    
    @Autowired
    private SysmenuService sysmenuService;
    // 切入点
    @Pointcut("@annotation(com.hy.sys.common.annotation.AuthRequire)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取鉴权注解
        AuthRequire authRequire = method.getAnnotation(AuthRequire.class);
        //获取 HttpServletRequest
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        String authcode = authRequire.value();
        String usertoken = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(usertoken)){
            throw new BusinessException(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMessage());
        }
        JSONObject jsonObject = JSONUtil.parseObj(usertoken);
        String roleId = jsonObject.get("roleid", String.class);
        if (StrUtil.isBlank(roleId)){
            throw new BusinessException(ResultCode.MISS_USER_INFO.getCode(),ResultCode.MISS_USER_INFO.getMessage());
        }
        List<String> powerCodeList = sysmenuService.getPowerCodeByRoleId(roleId);
        if(CollUtil.isEmpty(powerCodeList)){
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(),ResultCode.FORBIDDEN.getMessage());
        }
        if(!powerCodeList.contains(authcode)){
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(),ResultCode.FORBIDDEN.getMessage());
        }
        return joinPoint.proceed();

    }

}
