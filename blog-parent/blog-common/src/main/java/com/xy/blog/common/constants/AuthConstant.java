package com.xy.blog.common.constants;

/**
 *
 * @Description  权限相关常量定义
 * @Author yao
 * @Date 2023/5/25 11:32     
 **/
public interface AuthConstant {

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";
    /**
     * 默认角色
     */
    String DEFAULT_ROLE="ROLE_ADMIN";
    /**
     * 管理员类型
     */
    String ADMIN_ROLE_TYPE = "1";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "test-api";


    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/test-api/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";
    /**
     * 用户启用状态
     */
    Integer USER_ENABLE_STATUS = 1;

}