package com.hy.sys.kefu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Sysunit;
import com.hy.sys.kefu.vo.PageParam;
import com.hy.sys.kefu.vo.SysunitParam;
import com.hy.sys.service.SysunitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @ClassName SysunitController
 * description:
 * yao create 2023年07月29日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "机构管理接口",tags = {"SysunitController"})
public class SysunitController {
    @Autowired
    private SysunitService sysunitService;

    @ApiOperation(value = "获取机构列表接口")
    @PostMapping("unit/GetListPage")
    public CommonResult getUnitList(PageParam pageParam){
        IPage<Sysunit> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
        QueryWrapper<Sysunit> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(pageParam.getCondition())){
            queryWrapper.like("su.pame",pageParam.getCondition());
        }
        IPage<Sysunit> iPage = sysunitService.getListPage(page,queryWrapper);
        return CommonResult.success(new CommonPage<>(iPage));
    }

    @ApiOperation(value = "新增机构接口")
    @PostMapping("unit/add")
    public CommonResult addUnit(@Valid SysunitParam sysunitParam){
        Sysunit sysunit = new Sysunit();
        BeanUtils.copyProperties(sysunitParam,sysunit);
        sysunit.setTid(UUID.randomUUID().toString());
        sysunit.setAddtime(LocalDateTime.now());
        boolean save = sysunitService.save(sysunit);
        if(!save){
            return CommonResult.failed(ErrorMsgConstant.ADD_ERROR);
        }
        return CommonResult.success();
    }

    @ApiOperation(value = "获取机构详情接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "用户id",paramType = "query",required = true,dataType = "String",dataTypeClass = String.class),})
    @PostMapping("unit/Details")
    public CommonResult getUnitDetails(String id){
        Sysunit sysunit = sysunitService.getUnitById(id);
        return CommonResult.success(sysunit);
    }


    @ApiOperation(value = "更新站点接口")
    @PostMapping("unit/update")
    public CommonResult updateRole(@Valid SysunitParam sysunitParam){
        Sysunit sysunit = new Sysunit();
        BeanUtils.copyProperties(sysunitParam,sysunit);
        boolean flag = sysunitService.updateById(sysunit);
        if(!flag){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }

    @ApiOperation(value = "删除机构接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "用户id",paramType = "query",required = true,dataType = "String",dataTypeClass = String.class),})
    @PostMapping("unit/Delete")
    public CommonResult deleteUnit(String id){
        boolean b = sysunitService.removeById(id);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.DELETE_ERROR);
        }

        return CommonResult.success();
    }

}
