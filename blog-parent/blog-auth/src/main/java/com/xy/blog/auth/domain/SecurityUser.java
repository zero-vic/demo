package com.xy.blog.auth.domain;

import cn.hutool.core.collection.CollUtil;
import com.xy.blog.common.constants.AuthConstant;
import com.xy.blog.common.dto.UserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/24 18:02
 **/
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
        this.setEnabled(userDto.getStatus().equals(AuthConstant.USER_ENABLE_STATUS));
        this.setClientId(userDto.getClientId());
        this.authorities = CollUtil.toList(new SimpleGrantedAuthority("ADMIN"));
        if(CollUtil.isNotEmpty(userDto.getRoles())){
            authorities = CollUtil.newArrayList();
            userDto.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
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
