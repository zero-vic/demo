package com.xy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.entity.Role;
import com.xy.blog.mapper.RoleMapper;
import com.xy.blog.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
