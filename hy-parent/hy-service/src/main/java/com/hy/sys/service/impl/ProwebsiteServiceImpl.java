package com.hy.sys.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.common.enums.DelFlagEnum;
import com.hy.sys.entity.Prowebsite;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.mapper.ProwebsiteMapper;
import com.hy.sys.mapper.SyspersonsMapper;
import com.hy.sys.service.ProwebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点管理表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@Service
public class ProwebsiteServiceImpl extends ServiceImpl<ProwebsiteMapper, Prowebsite> implements ProwebsiteService {

    @Autowired
    SyspersonsMapper syspersonsMapper;

    @Override
    public List<Syspersons> getSiteSyspersons(String siteId) {
        QueryWrapper<Prowebsite> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Prowebsite::getTid,siteId);
        Prowebsite prowebsite = this.getOne(queryWrapper);
        String personids = prowebsite.getPersonids();
        if(StrUtil.isBlank(personids)){
            return new ArrayList<>();
        }
        String[] split = personids.split(",");

        QueryWrapper<Syspersons> syspersonsWrapper = new QueryWrapper<>();
        syspersonsWrapper.lambda().eq(Syspersons::getIsdel, DelFlagEnum.NORMAL.getCode())
                .in(Syspersons::getTid, Arrays.asList(split));

        return syspersonsMapper.selectList(syspersonsWrapper);
    }

    @Override
    public IPage<Prowebsite> getListPage(IPage<Prowebsite> page, QueryWrapper<Prowebsite> queryWrapper) {
        IPage<Prowebsite> listPage = this.getBaseMapper().getListPage(page, queryWrapper);
        // 处理personsids
        List<Prowebsite> records = listPage.getRecords();
        records.forEach(this::convertPersonsIds);
        listPage.setRecords(records);
        return listPage;
    }

    @Override
    public Prowebsite getWebsiteById(String id) {
        // 处理personsids
        Prowebsite prowebsite = this.getBaseMapper().getWebsiteById(id);
        convertPersonsIds(prowebsite);
        return prowebsite;
    }

    /**
     * 把用户id转换成名字
     * @param prowebsite
     */
    private void convertPersonsIds(Prowebsite prowebsite) {
        String personids = prowebsite.getPersonids();
        if(!StrUtil.isBlank(personids)){
            String[] split = personids.split(",");
            List<String> ids = Arrays.asList(split);
            List<Syspersons> syspersons = syspersonsMapper.selectBatchIds(ids);
            String personnames = syspersons.stream().map(Syspersons::getName).collect(Collectors.joining(","));
            prowebsite.setPersonnames(personnames);
        }
    }
}
