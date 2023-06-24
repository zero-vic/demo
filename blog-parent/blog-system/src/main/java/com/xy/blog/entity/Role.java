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
 * 角色表
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Getter
@Setter
@TableName("t_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "角色描述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "修改人员")
    @TableField("update_user")
    private String updateUser;

    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "创建人员")
    @TableField("create_user")
    private String createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "父级角色")
    @TableField("pid")
    private String pid;

    @ApiModelProperty(value = "逻辑删除（0 未删除、1 删除")
    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
