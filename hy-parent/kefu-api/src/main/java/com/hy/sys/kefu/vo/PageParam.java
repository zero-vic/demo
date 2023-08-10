package com.hy.sys.kefu.vo;

import lombok.Data;

/**
 * @ClassName PageParam
 * description: layui 默认传递分页参数
 * yao create 2023年07月20日
 * version: 1.0
 */
@Data
public class PageParam {
    private long page = 1;

    private long limit = 10;

    private String condition;
}
