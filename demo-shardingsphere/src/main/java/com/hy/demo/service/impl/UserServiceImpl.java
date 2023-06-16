package com.hy.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.entity.User;
import com.hy.demo.mapper.UserMapper;
import com.hy.demo.service.IUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/31 8:59
 **/
@Service
@DS("shardingSphere")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Override
    public IPage<User> getList(Long pageNum, Long pageSize) {
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(User::getCTime, LocalDateTime.now().plusDays(-20));
        return this.page(page,queryWrapper);
    }
}
