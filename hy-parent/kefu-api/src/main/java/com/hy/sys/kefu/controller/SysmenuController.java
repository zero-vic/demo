package com.hy.sys.kefu.controller;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.dto.MenuTreeDto;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Sysmenu;
import com.hy.sys.kefu.vo.SysmenuParam;
import com.hy.sys.service.SysmenuService;
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
import java.util.List;

/**
 * @ClassName SysmenuController
 * description:
 * yao create 2023年07月27日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(tags = {"SysmenuController"},value = "系统菜单接口")
public class SysmenuController {
    @Autowired
    private SysmenuService sysmenuService;

    @ApiOperation(value = "获取菜单列表接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "condition",value = "菜单名",paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("menu/GetList")
    public CommonResult getMenuList(String condition){
        QueryWrapper<Sysmenu> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(condition)){
            queryWrapper.lambda().like(Sysmenu::getName,condition);
        }
        queryWrapper.lambda().orderByDesc(Sysmenu::getOrders);
        List<Sysmenu> list = sysmenuService.list(queryWrapper);
        return CommonResult.success(list);
    }
    @ApiOperation(value = "获取树形列表接口")
    @PostMapping("menu/GetTreeList")
    public CommonResult getMenuTreeList(){
        List<MenuTreeDto> list = sysmenuService.getTreeList();
        return CommonResult.success(list);
    }
    @ApiOperation(value = "菜单新增接口")
    @PostMapping("menu/add")
    public CommonResult add(@Valid SysmenuParam sysmenuParam){
        Sysmenu sysmenu = new Sysmenu();
        BeanUtils.copyProperties(sysmenuParam,sysmenu);
        sysmenu.setTid(UUID.randomUUID().toString());
        boolean save = sysmenuService.save(sysmenu);
        if(!save){
            return CommonResult.failed(ErrorMsgConstant.ADD_ERROR);
        }
        return CommonResult.success();
    }
    @ApiOperation(value = "菜单详情接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "菜单id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("menu/Details")
    public CommonResult details(String id){
        Sysmenu sysmenu = sysmenuService.getById(id);
        return CommonResult.success(sysmenu);
    }
    @ApiOperation(value = "菜单更新接口")
    @PostMapping("menu/Update")
    public CommonResult update(@Valid SysmenuParam sysmenuParam){
        Sysmenu sysmenu = new Sysmenu();
        BeanUtils.copyProperties(sysmenuParam,sysmenu);
        boolean b = sysmenuService.updateById(sysmenu);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }
    @ApiOperation(value = "菜单删除接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "菜单id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("menu/Delete")
    public CommonResult delete(String id){
        boolean b = sysmenuService.removeById(id);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.DELETE_ERROR);
        }
        return CommonResult.success();
    }

}
