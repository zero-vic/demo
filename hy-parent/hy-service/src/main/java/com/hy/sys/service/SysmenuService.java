package com.hy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.common.dto.MenuTreeDto;
import com.hy.sys.entity.Sysmenu;


import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
public interface SysmenuService extends IService<Sysmenu> {
    /**
     * 根据roleId获取权限路径
     * @param roleid
     * @return
     */
    List<String> getPathByRoleId(String roleid);

    List<String> getPowerCodeByRoleId(String roleid);

    /**
     * 获取菜单treeList
     * @return
     */
    List<MenuTreeDto> getTreeList();
}
