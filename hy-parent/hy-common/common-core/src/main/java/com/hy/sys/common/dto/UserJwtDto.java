package com.hy.sys.common.dto;



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
    private String user_name;

    /**
     * client_id
     */
    private String client_id;
    /**
     * 角色ID
     **/
    private String roleid;
    /**
     * 机构ID
     **/
    private String unitid;
    /**
     * 角色类型
     **/
    private String roletype;
    /**
     * jti
     */
    private String jti;
    /**
     * 过期时间
     */
    private Long exp;

    private List<String> scope;

    private List<String> authorities;

}
