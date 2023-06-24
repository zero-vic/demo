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
 * 用户表
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Getter
@Setter
@TableName("t_user")
@ApiModel(value = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "登录密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "状态1：正常，0：锁定")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "最后一次登录时间")
    @TableField("last_login_date")
    private Date lastLoginDate;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人员")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "修改人员")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "逻辑删除（0 未删除、1 删除")
    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
