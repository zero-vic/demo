package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 站点管理表
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@TableName("pro_website")
@Data
public class Prowebsite implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 网站名称
     */
    @TableField("name")
    private String name;

    /**
     * 应用地址（网站域名）
     */
    @TableField("urls")
    private String urls;

    /**
     * 类型，暂时为空
     */
    @TableField("types")
    private String types;

    /**
     * 介绍
     */
    @TableField("contents")
    private String contents;

    /**
     * logo（主要是会话窗口展示）
     */
    @TableField("img")
    private String img;

    /**
     * 状态（0正常 1 关闭）
     */
    @TableField("states")
    private Integer states;

    /**
     * 入库时间
     */
    @TableField("addtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addtime;

    /**
     * 截止时间
     */
    @TableField("endtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endtime;

    /**
     * 联系人
     */
    @TableField("linkman")
    private String linkman;

    /**
     * 联系方式
     */
    @TableField("tels")
    private String tels;

    /**
     * 简称（网站）
     */
    @TableField("signs")
    private String signs;

    /**
     * 单位id
     */
    @TableField("unitid")
    private String unitid;

    /**
     * 客户人员id（使用,隔开）
     */
    @TableField("personids")
    private String personids;
    /**
     * 删除标识
     */
    @TableField("isdel")
    private Integer isdel;

    /**
     * 单位名称
     */
    @TableField(value = "unitname",exist = false)
    private String unitname;

    /**
     * 客户人员（使用,隔开）
     */
    @TableField(value = "personnames",exist = false)
    private String personnames;



}
