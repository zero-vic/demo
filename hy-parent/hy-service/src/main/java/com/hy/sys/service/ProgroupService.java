package com.hy.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Progroup;


/**
 * <p>
 * 群组表 服务类
 * </p>
 *
 * @author 
 * @since 2023-08-15
 */
public interface ProgroupService extends IService<Progroup> {

    IPage<Progroup> getGroupsByUserId(IPage<Progroup> iPage, String userId);
}
