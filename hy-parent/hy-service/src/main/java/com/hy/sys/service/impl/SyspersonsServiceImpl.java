package com.hy.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.dto.UserDto;
import com.hy.sys.common.vo.SyspersonsVo;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.mapper.SysmenuMapper;
import com.hy.sys.mapper.SyspersonsMapper;
import com.hy.sys.service.SyspersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private SysmenuMapper sysmenuMapper;
    @Override
    public UserDto loadUserByUsername(String username) {
        UserDto userDto = new UserDto();
        Syspersons syspersons = this.baseMapper.loadUserByUsername(username);
        if (syspersons!=null){
            userDto.setId(syspersons.getTid());
            userDto.setUsername(syspersons.getAccount());
            userDto.setPassword(syspersons.getPwd());
            userDto.setStatus(syspersons.getState());
            userDto.setClientId("test-id");
            userDto.setRoleId(syspersons.getRoleid());
            userDto.setUnitId(syspersons.getUnitid());
            userDto.setRoleType(StrUtil.toStringOrNull(syspersons.getRoletype()));
            userDto.setRoles(CollUtil.toList("ADMIN"));
//            List<String> powerCodes = sysmenuMapper.getPowerCodeByRoleId(syspersons.getRoleid());
//            userDto.setPowerCodes(powerCodes);
        }

        return userDto;
    }

    @Override
    public IPage<SyspersonsVo> getListPage(IPage<Syspersons> page, QueryWrapper<Syspersons> queryWrapper) {
        return this.baseMapper.getListPage(page, queryWrapper);
    }

    @Override
    public Syspersons getUserById(String id) {
        return this.baseMapper.getUserById(id);
    }

    @Override
    public List<TransferDto> getUserByUnitId(String unitid) {

        return this.baseMapper.getUserByUnitId(unitid);
    }


}
