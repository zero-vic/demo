package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@Data
public class Sysmenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
    @TableId("tid")
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

}
