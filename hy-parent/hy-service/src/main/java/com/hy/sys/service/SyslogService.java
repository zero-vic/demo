package com.hy.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Syslog;


/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author xy
 * @since 2023-06-08
 */
public interface SyslogService extends IService<Syslog> {

    IPage<Syslog> getListPage(IPage<Syslog> page, QueryWrapper<Syslog> queryWrapper);
}
