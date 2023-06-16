package com.xy.cloud.demo.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.cloud.demo.entity.Audit;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:15
 **/
@Mapper
public interface AuditMapper extends BaseMapper<Audit> {
}
