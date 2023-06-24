package com.xy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.entity.UserRole;
import com.xy.blog.mapper.UserRoleMapper;
import com.xy.blog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
