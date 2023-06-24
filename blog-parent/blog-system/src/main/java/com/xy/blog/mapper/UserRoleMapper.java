package com.xy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
