package com.hy.sys.kefu.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SyspersonsParam implements Serializable {
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
    @Pattern(regexp = "^[0-9a-zA-Z]{6,14}$",message = "登录名由6-14位的数字或者字母组成")
    private String account;
    /**
     * 密码
     */
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,14}$",message = "必须包含数字和字母且6-14位")
    private String pwd;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    private String name;

    /**
     * 电话
     */
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
    message = "电话号码格式错误")
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