package com.hy.sys.service.impl;


import com.hy.sys.entity.Syslog;
import com.hy.sys.mapper.SyslogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncSyslogService
 * description:异步日志service
 * yao create 2023年08月08日
 * version: 1.0
 */
@Service
public class AsyncSyslogService {
    @Autowired
    private SyslogMapper syslogMapper;

    @Async
    public void insert(Syslog syslog){
        syslogMapper.customInsert(syslog);
    }
}
