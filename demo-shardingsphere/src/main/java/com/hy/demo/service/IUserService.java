package com.hy.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.User;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/31 8:58
 **/
public interface IUserService extends IService<User> {
    IPage<User> getList(Long pageNum,Long pageSize);
}
