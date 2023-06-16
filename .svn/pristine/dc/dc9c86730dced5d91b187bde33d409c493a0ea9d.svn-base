package com.hy.demo.dto;



import java.io.Serializable;
import java.util.List;

/**
 * @Description 解析jwt对象
 * @Author yao
 * @Date 2023/5/24 17:58
 **/

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
    private String roleid;
    /**
     * 机构ID
     **/
    private String unitid;
    /**
     * 角色类型
     **/
    private String roletype;
    private List<String> authorities;

    public UserJwtDto(String id, String username, String clientId, String roleid, String unitid, String roletype, List<String> authorities) {
        this.id = id;
        this.username = username;
        this.clientId = clientId;
        this.roleid = roleid;
        this.unitid = unitid;
        this.roletype = roletype;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getRoletype() {
        return roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }
}
