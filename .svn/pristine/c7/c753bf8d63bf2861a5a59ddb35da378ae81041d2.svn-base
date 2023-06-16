package com.hy.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.entity.Zbmb4Infos;
import com.hy.demo.mapper.Zbmb4InfosMapper;
import com.hy.demo.service.IZbmb4InfosService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:39
 **/
@Service("zbmb4InfosService")
public class Zbmb4InfosServiceImpl extends ServiceImpl<Zbmb4InfosMapper, Zbmb4Infos> implements IZbmb4InfosService {
    @Override
    public IPage<Zbmb4Infos> getList(long pageNum, long pageSize) {
        Page<Zbmb4Infos> zbmb4InfosPage = this.getBaseMapper().selectPage(new Page<>(pageNum, pageSize), new QueryWrapper<>());
        return zbmb4InfosPage;
    }
}
