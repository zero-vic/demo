package com.hy.sys.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.vo.SyspersonsVo;
import com.hy.sys.entity.Syspersons;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    @Select("SELECT p.tid ,roleid,pwd,p.unitid,p.account account,p.\"state\" \"state\",r.\"type\" \"roletype\" FROM sys_persons p LEFT JOIN sys_roles r on p.roleid = r.tid where p.account = #{username} and p.isdel = 0")
    Syspersons loadUserByUsername(@Param("username") String username);


    @Select("<script>"+
            "SELECT " +
            "sp.*, " +
            "sr.\"name\" roleName, " +
            "su.pame unitname " +
            "FROM " +
            "sys_persons sp " +
            "LEFT JOIN sys_roles sr ON sp.roleid = sr.tid " +
            "LEFT JOIN sys_unit su ON sp.unitid = su.tid  " +
            "WHERE 1 = 1"+
            "<if test=\"ew!=null\"> " +
            " and  ${ew.sqlSegment} " +
            " </if> " +
            "</script>"
    )
    IPage<SyspersonsVo> getListPage(IPage<Syspersons> page, @Param("ew") Wrapper<Syspersons> queryWrapper);

    @Select("SELECT " +
            "sp.*, " +
            "sr.\"name\" rolename, " +
            "su.pame unitname " +
            "FROM " +
            "sys_persons sp " +
            "LEFT JOIN sys_roles sr ON sp.roleid = sr.tid " +
            "LEFT JOIN sys_unit su ON sp.unitid = su.tid  " +
            "WHERE sp.tid = #{id} ")
    Syspersons getUserById(String id);
    @Select("SELECT tid \"value\",\"name\" title FROM sys_persons WHERE isdel = 0 AND unitid = #{unitid}")
    List<TransferDto> getUserByUnitId(@Param("unitid") String unitid);
}
