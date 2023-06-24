package com.xy.blog.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 登陆用户信息
 * @Author yao
 * @Date 2023/5/24 17:58
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 状态
     */
    private Integer status;
    /**
     * client_id
     */
    private String clientId;
    /**
     * 角色ID
     **/
    private String roleId;
    /**
     * 角色类型
     **/
    private String roleType;
    private List<String> roles;

}
