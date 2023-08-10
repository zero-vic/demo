package com.hy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Syspowers;


import java.util.List;

/**
 * <p>
 * 权限配置表 服务类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
public interface SyspowersService extends IService<Syspowers> {
    /**
     * 更新权限
     * @param menuId
     * @param roleIds
     * @return
     */
    Boolean updatePowers(String menuId, List<String> roleIds);
}
