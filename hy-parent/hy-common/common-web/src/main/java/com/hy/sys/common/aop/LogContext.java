package com.hy.sys.common.aop;

import lombok.Data;

/**
 * @ClassName LogContext
 * description:
 * yao create 2023年08月11日
 * version: 1.0
 */
@Data
public class LogContext {
    private String content;
    public LogContext(){
        content = "null content";
    }
}
