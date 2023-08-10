package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Syspowers;
import com.hy.sys.entity.Sysroles;
import com.hy.sys.mapper.SysrolesMapper;
import com.hy.sys.service.SyspowersService;
import com.hy.sys.service.SysrolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Service
public class SysrolesServiceImpl extends ServiceImpl<SysrolesMapper, Sysroles> implements SysrolesService {

    @Autowired
    private SyspowersService syspowersService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleById(String id) {
        // 先删除role 再删power表中对应的角色的权限
        this.removeById(id);
        QueryWrapper<Syspowers> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Syspowers::getRoleid,id);
        syspowersService.remove(queryWrapper);
        return true;
    }
}
