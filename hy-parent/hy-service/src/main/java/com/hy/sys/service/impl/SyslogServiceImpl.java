package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Syslog;
import com.hy.sys.mapper.SyslogMapper;
import com.hy.sys.service.SyslogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-08
 */
@Service
public class SyslogServiceImpl extends ServiceImpl<SyslogMapper, Syslog> implements SyslogService {

    @Override
    public IPage<Syslog> getListPage(IPage<Syslog> page, QueryWrapper<Syslog> queryWrapper) {
        return this.getBaseMapper().getListPage(page,queryWrapper);
    }
}
