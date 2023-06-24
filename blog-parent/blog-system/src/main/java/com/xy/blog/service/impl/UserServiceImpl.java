package com.xy.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.common.constants.AuthConstant;
import com.xy.blog.common.dto.UserDto;
import com.xy.blog.entity.Role;
import com.xy.blog.entity.User;
import com.xy.blog.mapper.RoleMapper;
import com.xy.blog.mapper.UserMapper;
import com.xy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDto loadUserByUsername(String username) {
        UserDto userDto = new UserDto();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername,username);
        User user = getOne(queryWrapper);
        if(user!=null){
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setStatus(user.getStatus());
            userDto.setClientId(AuthConstant.ADMIN_CLIENT_ID);
            List<Role> roleList = roleMapper.getRoleByUserId(user.getId());
            if(CollUtil.isNotEmpty(roleList)){
                List<String> roles = roleList.stream().map(Role::getName).collect(Collectors.toList());
                userDto.setRoles(roles);
            }else{
                userDto.setRoles(CollUtil.newArrayList());
            }

        }
        return userDto;
    }
}
