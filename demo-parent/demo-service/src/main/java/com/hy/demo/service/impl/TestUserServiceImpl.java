package com.hy.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.dto.UserDto;
import com.hy.demo.entity.TestUser;
import com.hy.demo.mapper.TestUserMapper;
import com.hy.demo.service.ITestUserService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/25 10:11
 **/
@Service("testUserService")

public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser> implements ITestUserService {

    @Override
    public UserDto loadUserByUsername(String username) {
        UserDto userDto = new UserDto();
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TestUser::getUsername,username);
        TestUser testUser = this.getOne(queryWrapper);
        if(testUser != null){
            userDto.setId(testUser.getId());
            userDto.setUsername(testUser.getUsername());
            userDto.setPassword(testUser.getPassword());
            userDto.setStatus(1);
            userDto.setClientId("test");
            userDto.setRoles(CollUtil.toList("ADMIN"));
        }
        return userDto;
    }
}
