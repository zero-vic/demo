package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description
 * @Author yao
 * @Date 2023/6/1 17:12
 **/
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}
