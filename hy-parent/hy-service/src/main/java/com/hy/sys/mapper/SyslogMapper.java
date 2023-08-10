package com.hy.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.sys.entity.Syslog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 操作日志表 Mapper 接口
 * </p>
 *
 * @author xy
 * @since 2023-06-08
 */
@Mapper
public interface SyslogMapper extends BaseMapper<Syslog> {


    @Select("<script>"+
            "SELECT sl.*,su.pame unitname " +
            "from sys_log sl LEFT JOIN sys_unit su " +
            "on sl.unitid = su.tid "+
            "WHERE 1 = 1" +
            "<if test=\"ew.nonEmptyOfWhere\"> " +
            " AND " +
            "</if> " +
            " ${ew.sqlSegment} "+
            "</script>"
    )
    IPage<Syslog> getListPage(IPage<Syslog> page,@Param("ew") QueryWrapper<Syslog> queryWrapper);

    /**
     * pgsql 因为 user字段名出现bug
     * @param syslog
     */
    @Insert("INSERT INTO sys_log ( tid, unitid, userid, adddate, paths, logip, \"type\", model, \"user\", usertel,contents,services ) VALUES " +
            "(#{tid},#{unitid},#{userid},#{adddate},#{paths},#{logip},#{type},#{model},#{user},#{usertel},#{contents},#{services})")
    void customInsert(Syslog syslog);


}
