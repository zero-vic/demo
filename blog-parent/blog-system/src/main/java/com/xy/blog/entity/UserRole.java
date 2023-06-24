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
 * 用户角色关联表
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Getter
@Setter
@TableName("t_user_role")
@ApiModel(value = "UserRole对象", description = "用户角色关联表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty(value = "角色id")
    @TableId("role_id")
    private String roleId;
}
