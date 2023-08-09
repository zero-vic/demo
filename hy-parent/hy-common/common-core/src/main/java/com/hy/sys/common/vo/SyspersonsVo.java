package com.hy.sys.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName SyspersonsVo
 * description:
 * yao create 2023年07月27日
 * version: 1.0
 */
@Data
public class SyspersonsVo implements Serializable {
    /**
     * 机构人员id
     */
    private String tid;

    /**
     * 所属机构id
     */
    private String unitid;

    /**
     * 部门id
     */
    private String departmentid;

    /**
     * 用户角色id
     */
    private String roleid;

    /**
     * 登录名(普通用户把手机号写进去)
     */
    private String account;


    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 状态（1正常，2禁用）
     */
    private Integer state;

    /**
     * 是否删除（0未删除false，1已删除true）
     */
    private Integer isdel;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;

    /**
     * 头像 base64
     */
    private String img;

    /**
     * 是否单位账号  1是 0否
     */
    private Short isunitaccount;
    /**
     * 关联工作台 1.常规账号 2.借用账号 3.造价咨询 4.内页及部长 5.领导 6.其他
     */
    private Short workbench;

    /**
     * 登录方式:默认0:密码登录;1:验证码登录
     */
    private Short loginmode;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 是否开启客服模式（1开启 0 关闭）
     */
    private Integer services;

    /**
     * 角色名
     */
    private String rolename;
    /**
     * 机构名
     */
    private String unitname;
}
