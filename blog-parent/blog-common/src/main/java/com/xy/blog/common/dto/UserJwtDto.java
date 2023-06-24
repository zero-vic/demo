package com.xy.blog.common.dto;



import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 解析jwt对象
 * @Author yao
 * @Date 2023/5/24 17:58
 **/
@Data
public class UserJwtDto implements Serializable {

    private String id;
    /**
     * 用户名
     */
    private String username;

    /**
     * client_id
     */
    private String clientId;
    /**
     * 角色ID
     **/
    private String roleId;

    private List<String> authorities;
}
