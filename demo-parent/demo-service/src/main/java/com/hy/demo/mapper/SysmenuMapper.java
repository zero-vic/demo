package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.Sysmenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Mapper
public interface SysmenuMapper extends BaseMapper<Sysmenu> {

    @Select("SELECT DISTINCT path from sys_powers p LEFT JOIN sys_menu m on p.menuid = m.tid where p.roleid = #{roleid}")
    List<String> getPathByRoleId(@Param("roleid")String roleid);
    @Select("SELECT powercode from sys_powers p LEFT JOIN sys_menu m on p.menuid = m.tid where p.roleid = #{roleid}")
    List<String> getPowerCodeByRoleId(@Param("roleid") String roleid);
}
