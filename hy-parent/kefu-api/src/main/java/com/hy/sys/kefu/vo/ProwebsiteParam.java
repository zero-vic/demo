package com.hy.sys.kefu.vo;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * @ClassName ProwebsiteParam
 * description:
 * yao create 2023年08月07日
 * version: 1.0
 */
@Data
public class ProwebsiteParam {
    private String tid;

    /**
     * 网站名称
     */
    @NotNull(message = "网站名称不能为空")
    private String name;

    /**
     * 应用地址（网站域名）
     */
    @NotNull(message = "应用地址不能为空")
    private String urls;

    /**
     * 类型，暂时为空
     */
    private String types;

    /**
     * 介绍
     */
    private String contents;

    /**
     * logo（主要是会话窗口展示）
     */
    private String img;

    /**
     * 状态（0正常 1 关闭）
     */
    private Integer states;

    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

    /**
     * 截止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

    /**
     * 联系人
     */
    @NotNull(message = "网站名称不能为空")
    private String linkman;

    /**
     * 联系方式
     */
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "联系方式格式错误")
    private String tels;

    /**
     * 简称（网站）
     */
    @NotNull(message = "简称不能为空")
    private String signs;

    /**
     * 单位id
     */
    @NotNull(message = "单位不能为空")
    private String unitid;

    /**
     * 客户人员id（使用,隔开）
     */
    private String personids;
    /**
     * 删除标识
     */
    private Integer isdel;

    /**
     * 单位名称
     */
    private String unitname;

    /**
     * 客户人员（使用,隔开）
     */
    private String personnames;

}
