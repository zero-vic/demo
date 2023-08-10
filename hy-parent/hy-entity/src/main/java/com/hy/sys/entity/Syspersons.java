package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_persons")
@Data
public class Syspersons implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构人员id
     */
    @TableId("tid")
    private String tid;

    /**
     * 所属机构id
     */
    @TableField("unitid")
    private String unitid;

    /**
     * 部门id
     */
    @TableField("departmentid")
    private String departmentid;

    /**
     * 用户角色id
     */
    @TableField("roleid")
    private String roleid;

    /**
     * 登录名(普通用户把手机号写进去)
     */
    @TableField("account")
    private String account;

    /**
     * 密码
     */
    @TableField("pwd")
    private String pwd;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 状态（1正常，2禁用）
     */
    @TableField("state")
    private Integer state;

    /**
     * 是否删除（0未删除false，1已删除true）
     */
    @TableField("isdel")
    private Integer isdel;

    /**
     * 添加时间
     */
    @TableField("addtime")
    private LocalDateTime addtime;

    /**
     * 头像 base64
     */
    @TableField("img")
    private String img;

    /**
     * 是否单位账号  1是 0否
     */
    @TableField("isunitaccount")
    private Short isunitaccount;
    /**
         * 关联工作台 1.常规账号 2.借用账号 3.造价咨询 4.内页及部长 5.领导 6.其他
     */
    @TableField("workbench")
    private Short workbench;

    /**
     * 登录方式:默认0:密码登录;1:验证码登录
     */
    @TableField("loginmode")
    private Short loginmode;
    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;
    /**
     * 是否开启客服模式（1开启 0 关闭）
     */
    @TableField("services")
    private Integer services;
    /**
     * 角色类型
     */
    @TableField(value = "roletype",exist = false)
    private String roletype;

    /**
     * 角色名
     */
    @TableField(value = "rolename",exist = false)
    private String rolename;
    /**
     * 机构名
     */
    @TableField(value = "unitname",exist = false)
    private String unitname;


}
