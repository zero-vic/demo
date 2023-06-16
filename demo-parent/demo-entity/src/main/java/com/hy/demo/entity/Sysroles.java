package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_roles")
public class Sysroles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("tid")
    private String tid;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 说明
     */
    @TableField("description")
    private String description;

    /**
     * 添加时间
     */
    @TableField("addtime")
    private Date addtime;

    /**
     * 机构ID
     */
    @TableField("unitid")
    private String unitid;

    /**
     * 机构类型
     */
    @TableField("unittype")
    private Integer unittype;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public Integer getUnittype() {
        return unittype;
    }

    public void setUnittype(Integer unittype) {
        this.unittype = unittype;
    }

    @Override
    public String toString() {
        return "Sysroles{" +
        ", tid = " + tid +
        ", name = " + name +
        ", type = " + type +
        ", description = " + description +
        ", addtime = " + addtime +
        ", unitid = " + unitid +
        ", unittype = " + unittype +
        "}";
    }
}
