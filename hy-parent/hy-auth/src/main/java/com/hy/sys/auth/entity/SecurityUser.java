package com.hy.sys.auth.entity;

import cn.hutool.core.collection.CollUtil;
import com.hy.sys.common.dto.UserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class SecurityUser implements UserDetails {

    /**
     * ID
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 登录客户端ID
     */
    private String clientId;
    /**
     * 角色ID
     **/
    private String roleId;
    /**
     * 机构ID
     **/
    private String unitId;
    /**
      * 角色类型
     **/
    private String roleType;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser() {

    }
    public SecurityUser(UserDto userDto) {
        this.setId(userDto.getId());
        this.setUsername(userDto.getUsername());
        this.setPassword(userDto.getPassword());
        this.setEnabled(userDto.getStatus() == 1);
        this.setClientId(userDto.getClientId());
        this.setRoleId(userDto.getRoleId());
        this.setRoleType(userDto.getRoleType());
        this.setUnitId(userDto.getUnitId());
        this.authorities = CollUtil.toList(new SimpleGrantedAuthority("ADMIN"));

//        if (userDto.getRoles() != null) {
//            authorities = new ArrayList<>();
//            userDto.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
//        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}