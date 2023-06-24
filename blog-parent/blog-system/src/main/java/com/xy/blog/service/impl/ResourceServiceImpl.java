package com.xy.blog.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.blog.entity.Resource;
import com.xy.blog.mapper.ResourceMapper;
import com.xy.blog.po.ResourcePo;
import com.xy.blog.po.RolePo;
import com.xy.blog.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Override
    public Map<String, List<String>> getResRoleMap(String userId) {
        List<ResourcePo> resourceList = this.baseMapper.getRoleResListByUserId(userId);
        if(CollUtil.isNotEmpty(resourceList)){
           return resourceList.stream().collect(Collectors.toMap(ResourcePo::getUrl, res -> CollUtil.newArrayList(res.getRoles().stream().map(RolePo::getRoleName).collect(Collectors.toList()))));
        }
        return null;
    }
}
