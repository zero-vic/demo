package com.hy.demo.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Zbmb4Infos;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:38
 **/
public interface IZbmb4InfosService extends IService<Zbmb4Infos> {
    IPage<Zbmb4Infos> getList(long pageNum, long pageSize);
}
