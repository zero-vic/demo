package com.hy.sys.kefu.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * @ClassName ProgroupParam
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@Data
public class ProgroupParam {

    /**
     * 站点id
     */
    private String wzid;

    /**
     * 群名称
     */
    @NotNull(message = "群名称不能为空")
    private String qmc;

    /**
     * 群头像
     */
    private String qtx;

    /**
     * 群描述
     */
    private String qms;

    /**
     * 群公告
     */
    private String qgg;



}
