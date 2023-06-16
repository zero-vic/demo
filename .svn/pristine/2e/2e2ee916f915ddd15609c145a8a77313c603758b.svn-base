package com.hy.demo.annotation;

import java.lang.annotation.*;

/**
 * Description：权限标识注解
 * yao create 2023/6/7 11:31
 **/
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequire {
    /**
     * 权限标识
     */
    String value() default "";
}
