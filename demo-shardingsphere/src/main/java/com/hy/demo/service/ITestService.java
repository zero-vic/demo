package com.hy.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.demo.entity.Test;


/**
 * @Description
 * @Author yao
 * @Date 2023/6/1 17:13
 **/
public interface ITestService extends IService<Test> {

    public Test getMasterTest(Long id);
    public Test getSlaveTest(Long id);
}
