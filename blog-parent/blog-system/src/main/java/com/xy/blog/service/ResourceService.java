package com.xy.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xy.blog.entity.Resource;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
public interface ResourceService extends IService<Resource> {

    Map<String, List<String>> getResRoleMap(String userId);

}
