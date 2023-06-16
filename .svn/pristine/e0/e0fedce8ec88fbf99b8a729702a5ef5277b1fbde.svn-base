package com.hy.demo.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.demo.entity.Test;
import com.hy.demo.mapper.TestMapper;
import com.hy.demo.service.ITestService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author yao
 * @Date 2023/6/1 17:14
 **/
@Service
@DS("master")
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
    @Override
    public Test getMasterTest(Long id) {
        return this.getById(id);
    }

    @Override
    @DS("test")
    public Test getSlaveTest(Long id) {
        return this.getById(id);
    }
}
