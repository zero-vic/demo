package com.xy.cloud.demo.test.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xy.cloud.demo.entity.Zbmb4Infos;
import com.xy.cloud.demo.service.IZbmb4InfosService;
import com.xy.cloud.demo.test.mapper.Zbmb4InfosMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:39
 **/
@Service(version = "1.0.0",protocol = "dubbo")
public class Zbmb4InfosServiceImpl extends ServiceImpl<Zbmb4InfosMapper, Zbmb4Infos> implements IZbmb4InfosService {
    @Override
    public IPage<Zbmb4Infos> getList(long pageNum, long pageSize) {
        Page<Zbmb4Infos> zbmb4InfosPage = this.getBaseMapper().selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());
        return zbmb4InfosPage;
    }
}
