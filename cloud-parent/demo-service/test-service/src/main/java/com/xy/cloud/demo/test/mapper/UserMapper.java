package com.xy.cloud.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cloud.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}