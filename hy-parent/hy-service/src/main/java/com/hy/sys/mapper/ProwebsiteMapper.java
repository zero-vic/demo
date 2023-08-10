package com.hy.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.sys.entity.Prowebsite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 站点管理表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@Mapper
public interface ProwebsiteMapper extends BaseMapper<Prowebsite> {

    @Select("<script>"+
            "SELECT ws.*,su.pame unitname " +
            "from pro_website ws LEFT JOIN sys_unit su " +
            "on ws.unitid = su.tid "+
            "WHERE 1 = 1" +
            "<if test=\"ew.nonEmptyOfWhere\"> " +
            " AND " +
            "</if> " +
            " ${ew.sqlSegment} "+
            "</script>"
    )
    IPage<Prowebsite> getListPage(IPage<Prowebsite> page,@Param("ew") QueryWrapper<Prowebsite> queryWrapper);

    @Select("SELECT ws.*,su.pame unitname from pro_website ws LEFT JOIN sys_unit su on ws.unitid = su.tid where ws.tid = #{id} ")
    Prowebsite getWebsiteById(String id);
}
