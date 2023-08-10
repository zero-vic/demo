package com.hy.sys.kefu.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName SysmenuParam
 * description:
 * yao create 2023年08月07日
 * version: 1.0
 */
@Data
public class SysmenuParam {
    /**
     * 菜单编号
     */
    private String tid;

    /**
     * 父级菜单Id（为空表示根目录）
     */
    private String parentid;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单路径
     */
    @NotNull(message = "菜单路径不能为空")
    private String path;

    /**
     * 菜单图标路径
     */
    private String icon;

    /**
     * 排序序号
     */
    @Min(value = 0,message = "排序序号需要大于0")
    private Integer orders;

    /**
     * 菜单类型（1 高投 2造价咨询 3设计）
     */
    @NotNull(message = "菜单类型不能为空")
    private String types;

    /**
     * 是否显示到首页左侧菜单栏（1/true：显示；0/false：不显示）
     */
    private Integer isshow;

    /**
     * 跳转方式
     */
    private String target;

    /**
     * 权限类型，1、页面 2、按钮 3、数据
     */
    @NotNull(message = "权限类型不能为空")
    private String models;

    /**
     * 权限标识
     */
    @NotNull(message = "权限标识不能为空")
    private String powercode;

    /**
     * 菜单地址key
     */
    private String pathkey;

    /**
     * 菜单图标选中路径
     */
    private String iconselect;
}
