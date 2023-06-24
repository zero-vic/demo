package com.xy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.common.dto.UserDto;
import com.xy.blog.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
public interface UserService extends IService<User> {
    /**
     * 更据用户名获取用户信息
     * @param username
     * @return
     */
    UserDto loadUserByUsername(String username);

}
