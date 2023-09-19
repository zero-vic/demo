## AOP+注解实现自定义日志功能

### 需求描述

> 部分系统模块的接口需要收集日志然后存到数据库中，日志内容需要自定义

### 实现方案

1.定义一个注解

```java
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {
    /**
     * 模块
     */
    String model() default "";

    /**
     * 操作类型
     */
    BusinessType type() default BusinessType.OTHER;

    /**
     * 日志内容
     */
    String contents() default "";

}
```

2.接受自定义日志的实体类

```java
@Data
public class LogContext {
    private String content;
    public LogContext(){
        content = "null content";
    }
}
```

3.切面类

```java
@Aspect
@Component
@Slf4j
public class SysLogAspect {


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
			// 获取用户信息


       		//获取自定义日志信息
            int type = sysLog.type().getCode();
            String model = sysLog.model();
            String contents =  generateKeyBySpEL(sysLog.contents(), (ProceedingJoinPoint) joinPoint);
            // 异步存日志信息
            asyncSyslogService.insert(syslog);
        } catch (Exception ex) {
            log.error("系统日志异常信息：{}",ex.getMessage(),ex);
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


    private SpelExpressionParser parserSpel = new SpelExpressionParser();
    private DefaultParameterNameDiscoverer parameterNameDiscoverer= new DefaultParameterNameDiscoverer();
	// 使用spel解析获取值
    public String generateKeyBySpEL(String key, ProceedingJoinPoint pjp) {
        Expression expression = parserSpel.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return expression.getValue(context).toString();
    }


}
```

4.在接口使用

```java
    @SysLog(model = "删除角色",type = BusinessType.DELETE,contents = "#context.content")
    public CommonResult deleteRole(String id, LogContext context){

        boolean b = sysrolesService.deleteRoleById(id);
        // 拿到角色信息，
        context.setContent("测试自定义日志：删除角色是否成功："+b);
        return CommonResult.success();
    }
```

