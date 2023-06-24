package com.xy.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Getter
@Setter
@TableName("t_resource")
@ApiModel(value = "Resource对象", description = "资源表")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "资源名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "资源描述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "资源序号")
    @TableField("seq")
    private Integer seq;

    @ApiModelProperty(value = "资源地址")
    @TableField("url")
    private String url;

    @ApiModelProperty(value = "父级ID")
    @TableField("pid")
    private String pid;

    @ApiModelProperty(value = "0-菜单，1-功能 引用数据字典表")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "创建用户")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改用户")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除（0 未删除、1 删除")
    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
