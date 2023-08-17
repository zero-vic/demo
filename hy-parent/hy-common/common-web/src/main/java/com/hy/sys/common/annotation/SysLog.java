package com.hy.sys.common.annotation;

import com.hy.sys.common.enums.BusinessType;

import java.lang.annotation.*;

/**
 * Description: 系统日志记录注解
 * yao create
 *
 * @author Administrator
 */
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
