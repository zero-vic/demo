package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.common.dto.MenuTreeDto;
import com.hy.sys.entity.Sysmenu;
import com.hy.sys.mapper.SysmenuMapper;
import com.hy.sys.service.SysmenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Service("sysmenuService")
public class SysmenuServiceImpl extends ServiceImpl<SysmenuMapper, Sysmenu> implements SysmenuService {

    @Override
    public List<String> getPathByRoleId(String roleid) {
        List<String> pathList = this.baseMapper.getPathByRoleId(roleid);
        //去除空值
        return pathList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<String> getPowerCodeByRoleId(String roleid) {
        List<String> powerCodeList = this.baseMapper.getPowerCodeByRoleId(roleid);
        return powerCodeList;
    }

    @Override
    public List<MenuTreeDto> getTreeList() {
        return this.baseMapper.getTreeList();
    }
}
