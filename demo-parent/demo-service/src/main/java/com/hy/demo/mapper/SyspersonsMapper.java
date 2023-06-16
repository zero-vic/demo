package com.hy.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hy.demo.entity.Syspersons;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@Mapper
public interface SyspersonsMapper extends BaseMapper<Syspersons> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Select("SELECT p.tid ,roleid,pwd,p.unitid,p.\"name\" \"name\",\"state\",r.\"type\" \"roletype\" FROM sys_persons p LEFT JOIN sys_roles r on p.roleid = r.tid where p.\"name\" = #{username} and p.isdel = 0")
    Syspersons loadUserByUsername(@Param("username") String username);

}
