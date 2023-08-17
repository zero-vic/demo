package com.hy.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.sys.entity.Progroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 群组表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-08-15
 */
@Mapper
public interface ProgroupMapper extends BaseMapper<Progroup> {

    @Select("SELECT pg.* FROM pro_group_member gm JOIN pro_group pg on gm.qid = pg.tid where gm.yhid = #{userId}} and pg.qzt = 0 ")
    IPage<Progroup> getGroupsByUserId(IPage<Progroup> page, @Param("userId") String userId);
}
