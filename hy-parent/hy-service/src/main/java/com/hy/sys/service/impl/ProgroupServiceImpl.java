package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Progroup;
import com.hy.sys.mapper.ProgroupMapper;
import com.hy.sys.service.ProgroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群组表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-08-15
 */
@Service
public class ProgroupServiceImpl extends ServiceImpl<ProgroupMapper, Progroup> implements ProgroupService {

    @Override
    public IPage<Progroup> getGroupsByUserId(IPage<Progroup> iPage, String userId) {

        return this.baseMapper.getGroupsByUserId(iPage,userId);
    }
}
