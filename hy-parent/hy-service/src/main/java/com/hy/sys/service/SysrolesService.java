package com.hy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Sysroles;


/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
public interface SysrolesService extends IService<Sysroles> {

    boolean deleteRoleById(String id);
}
