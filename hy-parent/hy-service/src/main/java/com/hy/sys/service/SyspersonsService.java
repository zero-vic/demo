package com.hy.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.dto.UserDto;
import com.hy.sys.common.vo.SyspersonsVo;
import com.hy.sys.entity.Syspersons;


import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
public interface SyspersonsService extends IService<Syspersons> {
    /**
     * 获取用户信息
     * @param username
     * @return UserDto
     */
    UserDto loadUserByUsername(String username);

    IPage<SyspersonsVo> getListPage(IPage<Syspersons> page, QueryWrapper<Syspersons> queryWrapper);

    Syspersons getUserById(String id);

    List<TransferDto> getUserByUnitId(String unitid);
}
