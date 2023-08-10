package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author xy
 * @since 2023-06-08
 */
@TableName("sys_log")
@Data
public class Syslog implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 机构Id
     */
    @TableField("unitid")
    private String unitid;

    /**
     * 操作人id
     */
    @TableField("userid")
    private String userid;

    /**
     * 操作内容
     */
    @TableField("contents")
    private String contents;

    /**
     * 操作时间
     */
    @TableField("adddate")
    private LocalDateTime adddate;

    /**
     * 操作页面地址
     */
    @TableField("paths")
    private String paths;

    /**
     * 操作IP
     */
    @TableField("logip")
    private String logip;

    /**
     * 操作类型（1.增加 2.修改 3.删除 4.查询 5.登录/退出）
     */
    @TableField("type")
    private Integer type;

    /**
     * 操作数据Id
     */
    @TableField("dataid")
    private String dataid;

    /**
     * 微服务标识
     */
    @TableField("services")
    private String services;

    /**
     * 模块
     */
    @TableField("model")
    private String model;

    /**
     * 用户姓名
     */
    @TableField("user")
    private String user;

    /**
     * 电话
     */
    @TableField("usertel")
    private String usertel;
    /**
     * 机构名称
     */
    @TableField(value = "unitname",exist = false)
    private String unitname;


}
