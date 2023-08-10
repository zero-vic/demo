package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统机构信息表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_unit")
@Data
public class Sysunit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("tid")
    private String tid;

    /**
     * 父级ID 暂不使用
     */
    @TableField("parentid")
    private String parentid;

    /**
     * 机构全称
     */
    @TableField("pame")
    private String pame;

    /**
     * 简称 
     */
    @TableField("shortname")
    private String shortname;

    /**
     * 代码
     */
    @TableField("code")
    private String code;

    /**
     * 单位类型（1 高投 2造价咨询 3设计）
     */
    @TableField("type")
    private String type;

    /**
     * 联系人
     */
    @TableField("linkman")
    private String linkman;

    /**
     * 联系电话
     */
    @TableField("tel")
    private String tel;

    /**
     * 联系地址
     */
    @TableField("address")
    private String address;

    /**
     * 区域ID
     */
    @TableField("areaid")
    private String areaid;

    /**
     * 简介
     */
    @TableField("workrange")
    private String workrange;

    /**
     * 经度 暂不使用
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 纬度 暂不使用
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 机构类型 暂不使用
     */
    @TableField("uniontype")
    private String uniontype;

    /**
     * 添加时间
     */
    @TableField("addtime")
    private LocalDateTime addtime;

    /**
     * 状态 0 正常 1  禁用
     */
    @TableField("state")
    private Integer state;

    /**
     * 关联的管理帐号
     */
    @TableField("adminid")
    private String adminid;
    /**
     * 管理账号名
     */
    @TableField(value = "adminname",exist = false)
    private String adminname;


}
