package com.hy.sys.kefu.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName RoleVo
 * description:
 * yao create 2023年07月20日
 * version: 1.0
 */
@Data
public class RoleVo {
    private String id;

    private Integer roleType;
    @NotNull(message = "角色名称不能为空")
    private String roleName;

    private String roleDescription;
}
