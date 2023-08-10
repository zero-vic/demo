package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Sysunit;
import com.hy.sys.mapper.SysunitMapper;
import com.hy.sys.service.SysunitService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统机构信息表 服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Service
public class SysunitServiceImpl extends ServiceImpl<SysunitMapper, Sysunit> implements SysunitService {

    @Override
    public IPage<Sysunit> getListPage(IPage<Sysunit> page, QueryWrapper<Sysunit> queryWrapper) {
        return this.baseMapper.getListPage(page,queryWrapper);
    }

    @Override
    public Sysunit getUnitById(String id) {
        return this.baseMapper.getUnitById(id);
    }

}
