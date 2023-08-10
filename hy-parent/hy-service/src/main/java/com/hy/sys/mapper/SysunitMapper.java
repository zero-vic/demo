package com.hy.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.sys.entity.Sysunit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统机构信息表 Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Mapper
public interface SysunitMapper extends BaseMapper<Sysunit> {



    @Select("<script>" +
            "SELECT " +
            "su.*, " +
            "sp.\"name\" adminname " +
            "FROM " +
            "sys_unit su " +
            "LEFT JOIN sys_persons sp ON su.adminid = sp.tid " +
            "WHERE 1 = 1" +
            "<if test=\"ew.nonEmptyOfWhere\"> " +
            " AND " +
            "</if> " +
            " ${ew.sqlSegment} "+
            "</script>")
    IPage<Sysunit> getListPage(IPage<Sysunit> page,@Param("ew") Wrapper<Sysunit> queryWrapper);

    @Select("SELECT su.*,sp.\"name\" adminname FROM sys_unit su " +
            "LEFT JOIN sys_persons sp ON su.adminid = sp.tid " +
            "WHERE su.tid = #{id}")
    Sysunit getUnitById(String id);
}
