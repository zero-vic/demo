package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/31 8:56
 **/
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
