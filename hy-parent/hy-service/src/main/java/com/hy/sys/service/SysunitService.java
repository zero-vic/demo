package com.hy.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Sysunit;


/**
 * <p>
 * 系统机构信息表 服务类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
public interface SysunitService extends IService<Sysunit> {

    IPage<Sysunit> getListPage(IPage<Sysunit> page, QueryWrapper<Sysunit> queryWrapper);

    Sysunit getUnitById(String id);
}
