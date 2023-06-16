package com.hy.demo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.dto.UserDto;
import com.hy.demo.entity.Syspersons;
import com.hy.demo.mapper.SyspersonsMapper;
import com.hy.demo.service.SyspersonsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Service
public class SyspersonsServiceImpl extends ServiceImpl<SyspersonsMapper, Syspersons> implements SyspersonsService {

    @Override
    public UserDto loadUserByUsername(String username) {
        UserDto userDto = new UserDto();
        Syspersons syspersons = this.baseMapper.loadUserByUsername(username);
        if (syspersons!=null){
            userDto.setId(syspersons.getTid());
            userDto.setUsername(syspersons.getName());
            userDto.setPassword(syspersons.getPwd());
            userDto.setStatus(syspersons.getState());
            userDto.setClientId("test-id");
            userDto.setRoleId(syspersons.getRoleid());
            userDto.setUnitId(syspersons.getUnitid());
            userDto.setRoleType(StrUtil.toStringOrNull(syspersons.getRoletype()));
            userDto.setRoles(CollUtil.toList("ADMIN"));
        }

        return userDto;
    }
}
