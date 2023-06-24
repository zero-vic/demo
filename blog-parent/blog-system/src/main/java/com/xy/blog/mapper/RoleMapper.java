package com.xy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据userId获取角色列表
     * @param userId
     * @return
     */
    List<Role> getRoleByUserId(@Param("userId") String userId);

}
