package com.hy.sys.common.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.hy.sys.common.annotation.SysLog;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.enums.BusinessType;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.utils.IpUtil;
import com.hy.sys.entity.Syslog;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.service.SyspersonsService;
import com.hy.sys.service.impl.AsyncSyslogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author yao
 *  LogAspect
 * description: 系统日志切面类
 * yao create 2023年08月07日
 * version: 1.0
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    @Value("${spring.application.name}")
    private String serverName;
    @Autowired
    private AsyncSyslogService asyncSyslogService;
    @Autowired
    private SyspersonsService syspersonsService;

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.hy.sys.common.annotation.SysLog)")
    public void logPointCut(){}


    /**
     * 处理完请求后执行
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult){
        handleLog(joinPoint,null,jsonResult);
    }


    /**
     * 处理异常操作
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        handleLog(joinPoint,e,null);
    }


    protected void handleLog(JoinPoint joinPoint,Exception e,Object jsonObject){

        try {
            // 获取注解
            SysLog sysLog = getAnnotation(joinPoint);
            if(sysLog == null) {
                return;
            }
            //获取 HttpServletRequest JSONUtil.parseObj(jsonObject).getJSONObject("data").getStr("token")
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
            Syslog syslog = new Syslog();
            syslog.setType(sysLog.type().getCode());
            syslog.setModel(sysLog.model());
            syslog.setServices(serverName);
            // 登录单独处理
            if(sysLog.type() == BusinessType.LOGIN){
                if(((CommonResult) jsonObject).getCode() == 200){
                    String token = JSONUtil.parseObj(jsonObject).getJSONObject("data").getStr("token");
                    String id = JWTUtil.parseToken(token).getPayloads().getStr("id");
                    syslog = setUserInfo(syslog, request, id);
                }else {
                    return;
                }
            } else {
                syslog = setUserinfo(syslog, request);
                if(syslog == null){
                    return;
                }
            }

            // todo 生成规则确认后再修改
            syslog.setContents("contents");
            asyncSyslogService.insert(syslog);
        } catch (Exception ex) {
            log.error("系统日志异常信息：{}",ex.getMessage());
        }


    }

    /**
     * 获取注解
     * @param joinPoint
     * @return
     */
    private SysLog getAnnotation(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method!=null){
            return method.getAnnotation(SysLog.class);
        }
        return null;
    }

    private Syslog setUserinfo(Syslog syslog,HttpServletRequest request){
        String userInfo = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
        if(StrUtil.isEmpty(userInfo)){
            return null;
        }
        syslog.setTid(UUID.randomUUID().toString());
        syslog.setAdddate(LocalDateTime.now());
        JSONObject jsonObject = JSONUtil.parseObj(userInfo);
        String ipAddress = IpUtil.getIpAddress(request);
        syslog.setLogip(ipAddress);
        String requestURI = request.getRequestURI();
        syslog.setPaths(requestURI);

        String id = jsonObject.getStr("id");
        syslog.setUserid(id);
        Syspersons user = syspersonsService.getUserById(id);
        if(user!=null){
            syslog.setUnitid(user.getUnitid());
            syslog.setUnitname(user.getUnitname());
            syslog.setUsertel(user.getPhone());
            syslog.setUser(user.getAccount());
        }else {
            syslog.setUnitid(jsonObject.getStr("unitid"));
            syslog.setUser(jsonObject.getStr("user_name"));
        }
        return syslog;

    }
    private Syslog setUserInfo(Syslog syslog,HttpServletRequest request,String userid){
        syslog.setTid(UUID.randomUUID().toString());
        syslog.setAdddate(LocalDateTime.now());
        String ipAddress = IpUtil.getIpAddress(request);
        syslog.setLogip(ipAddress);
        String requestURI = request.getRequestURI();
        syslog.setPaths(requestURI);
        syslog.setUserid(userid);
        Syspersons user = syspersonsService.getUserById(userid);
        if(user!=null){
            syslog.setUnitid(user.getUnitid());
            syslog.setUnitname(user.getUnitname());
            syslog.setUsertel(user.getPhone());
            syslog.setUser(user.getAccount());
        }
        return syslog;
    }


}
