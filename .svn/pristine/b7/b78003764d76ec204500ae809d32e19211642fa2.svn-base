package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 系统机构信息表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_unit")
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
    private Date addtime;

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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getPame() {
        return pame;
    }

    public void setPame(String pame) {
        this.pame = pame;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getWorkrange() {
        return workrange;
    }

    public void setWorkrange(String workrange) {
        this.workrange = workrange;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getUniontype() {
        return uniontype;
    }

    public void setUniontype(String uniontype) {
        this.uniontype = uniontype;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    @Override
    public String toString() {
        return "Sysunit{" +
        ", tid = " + tid +
        ", parentid = " + parentid +
        ", pame = " + pame +
        ", shortname = " + shortname +
        ", code = " + code +
        ", type = " + type +
        ", linkman = " + linkman +
        ", tel = " + tel +
        ", address = " + address +
        ", areaid = " + areaid +
        ", workrange = " + workrange +
        ", longitude = " + longitude +
        ", latitude = " + latitude +
        ", uniontype = " + uniontype +
        ", addtime = " + addtime +
        ", state = " + state +
        ", adminid = " + adminid +
        "}";
    }
}
