package com.hy.sys.kefu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.annotation.SysLog;
import com.hy.sys.common.enums.BusinessType;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Sysroles;
import com.hy.sys.kefu.vo.PageParam;
import com.hy.sys.kefu.vo.RoleVo;
import com.hy.sys.service.SysrolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName SysController
 * description:
 * yao create 2023年07月20日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "系统角色管理接口",tags = {"SysroleController"})
public class SysroleController {

    @Autowired
    private SysrolesService sysrolesService;

    @ApiOperation(value = "获取角色列表接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "角色id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("role/GetListPage")
    public CommonResult getRoleList(PageParam pageParam){
        IPage<Sysroles> page = new Page<>(pageParam.getPage(), pageParam.getLimit());

        QueryWrapper<Sysroles> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getCondition())){
            queryWrapper.lambda().like(Sysroles::getName,pageParam.getCondition());
        }
        IPage<Sysroles> iPage = sysrolesService.page(page,queryWrapper);

        return CommonResult.success(new CommonPage<>(iPage));
    }
    @ApiOperation(value = "新增角色接口")
    @PostMapping("role/add")
    public CommonResult addRole(RoleVo roleVo){
        Sysroles sysroles = new Sysroles();
        sysroles.setTid(UUID.randomUUID().toString());
        sysroles.setDescription(roleVo.getRoleDescription());
        sysroles.setName(roleVo.getRoleName());
        sysroles.setType(roleVo.getRoleType());
        sysroles.setAddtime(LocalDateTime.now());
        boolean save = sysrolesService.save(sysroles);
        if(save){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "获取角色详情接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "角色id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("role/Details")
    public CommonResult getRoleDetails(String id){
        Sysroles sysroles = sysrolesService.getById(id);
        RoleVo roleVo = new RoleVo();
        if(sysroles!=null){
            roleVo.setId(sysroles.getTid());
            roleVo.setRoleName(sysroles.getName());
            roleVo.setRoleType(sysroles.getType());
            roleVo.setRoleDescription(sysroles.getDescription());
        }
        return CommonResult.success(roleVo);
    }

    @ApiOperation(value = "更新角色接口")
    @PostMapping("role/update")
    public CommonResult updateRole(RoleVo roleVo){
        Sysroles sysroles = new Sysroles();
        sysroles.setTid(roleVo.getId());
        sysroles.setDescription(roleVo.getRoleDescription());
        sysroles.setName(roleVo.getRoleName());
        sysroles.setType(roleVo.getRoleType());
        sysroles.setAddtime(LocalDateTime.now());
        boolean flag = sysrolesService.updateById(sysroles);
        if(flag){
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @ApiOperation(value = "删除角色接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "角色id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("role/Delete")
    @SysLog(model = "删除角色",type = BusinessType.DELETE)
    public CommonResult deleteRole(String id){
        boolean b = sysrolesService.deleteRoleById(id);
        return CommonResult.success();
    }


}
