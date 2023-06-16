package com.hy.demo.auth.service;

import com.hy.demo.auth.entity.SecurityUser;
import com.hy.demo.constants.MessageConstant;
import com.hy.demo.dto.UserDto;
import com.hy.demo.service.SyspersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/24 17:32
 **/
@Service
public class UserServiceImpl implements UserDetailsService {

//    @Autowired
//    @Qualifier("testUserService")
//    private ITestUserService testUserService;
    @Autowired
    private SyspersonsService syspersonsService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        UserDto userDto = testUserService.loadUserByUsername(username);
        UserDto userDto = syspersonsService.loadUserByUsername(username);
        if (userDto == null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        SecurityUser securityUser = new SecurityUser(userDto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }
}
