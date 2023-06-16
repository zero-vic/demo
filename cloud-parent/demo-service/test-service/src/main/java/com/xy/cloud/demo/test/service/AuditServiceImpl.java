package com.xy.cloud.demo.test.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cloud.demo.entity.Audit;
import com.xy.cloud.demo.service.IAuditService;
import com.xy.cloud.demo.test.mapper.AuditMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:18
 **/
@Service(version = "1.0.0",protocol = "dubbo")
public class AuditServiceImpl extends ServiceImpl<AuditMapper, Audit> implements IAuditService {
}
