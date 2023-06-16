package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_persons")
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
    private Date addtime;

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

    @TableField(value = "roletype" ,exist = false)
    private Integer roletype;

    public Integer getRoletype() {
        return roletype;
    }

    public void setRoletype(Integer roletype) {
        this.roletype = roletype;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(String departmentid) {
        this.departmentid = departmentid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Short getIsunitaccount() {
        return isunitaccount;
    }

    public void setIsunitaccount(Short isunitaccount) {
        this.isunitaccount = isunitaccount;
    }

    public Short getWorkbench() {
        return workbench;
    }

    public void setWorkbench(Short workbench) {
        this.workbench = workbench;
    }

    public Short getLoginmode() {
        return loginmode;
    }

    public void setLoginmode(Short loginmode) {
        this.loginmode = loginmode;
    }

    @Override
    public String toString() {
        return "Syspersons{" +
        ", tid = " + tid +
        ", unitid = " + unitid +
        ", departmentid = " + departmentid +
        ", roleid = " + roleid +
        ", account = " + account +
        ", pwd = " + pwd +
        ", name = " + name +
        ", phone = " + phone +
        ", state = " + state +
        ", isdel = " + isdel +
        ", addtime = " + addtime +
        ", img = " + img +
        ", isunitaccount = " + isunitaccount +
        ", workbench = " + workbench +
        ", loginmode = " + loginmode +
        ", roletype = " + roletype +
        "}";
    }
}
