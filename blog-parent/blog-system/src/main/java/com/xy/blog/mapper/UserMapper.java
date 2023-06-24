package com.xy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
