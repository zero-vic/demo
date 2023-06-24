package com.xy.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 角色资源表
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Getter
@Setter
@TableName("t_role_resource")
@ApiModel(value = "RoleResource对象", description = "角色资源表")
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @TableId("role_id")
    private String roleId;

    @ApiModelProperty(value = "资源id")
    @TableId("resource_id")
    private String resourceId;
}
