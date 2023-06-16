package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_menu")
public class Sysmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
    @TableField("tid")
    private String tid;

    /**
     * 父级菜单Id（为空表示根目录）
     */
    @TableField("parentid")
    private String parentid;

    /**
     * 菜单名称
     */
    @TableField("name")
    private String name;

    /**
     * 菜单路径
     */
    @TableField("path")
    private String path;

    /**
     * 菜单图标路径
     */
    @TableField("icon")
    private String icon;

    /**
     * 排序序号
     */
    @TableField("orders")
    private Integer orders;

    /**
     * 菜单类型（1 高投 2造价咨询 3设计）
     */
    @TableField("types")
    private String types;

    /**
     * 是否显示到首页左侧菜单栏（1/true：显示；0/false：不显示）
     */
    @TableField("isshow")
    private Integer isshow;

    /**
     * 跳转方式
     */
    @TableField("target")
    private String target;

    /**
     * 权限类型，1、页面 2、按钮 3、数据
     */
    @TableField("models")
    private String models;

    /**
     * 权限标识
     */
    @TableField("powercode")
    private String powercode;

    /**
     * 菜单地址key
     */
    @TableField("pathkey")
    private String pathkey;

    /**
     * 菜单图标选中路径
     */
    @TableField("iconselect")
    private String iconselect;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getPowercode() {
        return powercode;
    }

    public void setPowercode(String powercode) {
        this.powercode = powercode;
    }

    public String getPathkey() {
        return pathkey;
    }

    public void setPathkey(String pathkey) {
        this.pathkey = pathkey;
    }

    public String getIconselect() {
        return iconselect;
    }

    public void setIconselect(String iconselect) {
        this.iconselect = iconselect;
    }

    @Override
    public String toString() {
        return "Sysmenu{" +
        ", tid = " + tid +
        ", parentid = " + parentid +
        ", name = " + name +
        ", path = " + path +
        ", icon = " + icon +
        ", orders = " + orders +
        ", types = " + types +
        ", isshow = " + isshow +
        ", target = " + target +
        ", models = " + models +
        ", powercode = " + powercode +
        ", pathkey = " + pathkey +
        ", iconselect = " + iconselect +
        "}";
    }
}
