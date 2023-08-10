package com.hy.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Prowebsite;
import com.hy.sys.entity.Syspersons;


import java.util.List;

/**
 * <p>
 * 站点管理表 服务类
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
public interface ProwebsiteService extends IService<Prowebsite> {
    /**
     * 站点客服 列表
     * @param siteId
     * @return
     */
    List<Syspersons> getSiteSyspersons(String siteId);

    IPage<Prowebsite> getListPage(IPage<Prowebsite> page, QueryWrapper<Prowebsite> queryWrapper);

    Prowebsite getWebsiteById(String id);
}
