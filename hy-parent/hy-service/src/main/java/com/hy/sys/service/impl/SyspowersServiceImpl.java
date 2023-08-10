package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Sysmenu;
import com.hy.sys.entity.Syspowers;
import com.hy.sys.mapper.SysmenuMapper;
import com.hy.sys.mapper.SyspowersMapper;
import com.hy.sys.service.SyspowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限配置表 服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Service
public class SyspowersServiceImpl extends ServiceImpl<SyspowersMapper, Syspowers> implements SyspowersService {

    @Autowired
    private SysmenuMapper sysmenuMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePowers(String menuId, List<String> roleIds) {

        Sysmenu sysmenu = sysmenuMapper.selectById(menuId);
        if(sysmenu==null){
            return false;
        }
        String powercode = sysmenu.getPowercode();
        // 先删 再加
        QueryWrapper<Syspowers> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Syspowers::getMenuid,menuId);
        this.remove(queryWrapper);
        if(!CollectionUtils.isEmpty(roleIds)){
            List<Syspowers> syspowersList = roleIds.stream().map(roleId -> {
                Syspowers syspowers = new Syspowers();
                syspowers.setTid(UUID.randomUUID().toString());
                syspowers.setMenuid(menuId);
                syspowers.setRoleid(roleId);
                syspowers.setAddtime(LocalDateTime.now());
                syspowers.setMenucode(powercode);
                return syspowers;
            }).collect(Collectors.toList());
            this.saveBatch(syspowersList);
        }

        return true;
    }
}
