package com.xy.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xy.blog.entity.Resource;
import com.xy.blog.po.ResourcePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-06-23
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {
    List<ResourcePo> getRoleResListByUserId(@Param("userId") String userId);

}
