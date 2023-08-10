package com.hy.sys.kefu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Syspowers;
import com.hy.sys.kefu.vo.PowersVo;
import com.hy.sys.service.SyspowersService;
import com.hy.sys.service.SysrolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SyspowersController
 * description:
 * yao create 2023年08月01日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "系统权限管理接口",tags = {"SyspersonsController"})
public class SyspowersController {

    @Autowired
    private SyspowersService syspowersService;

    @Autowired
    private SysrolesService sysrolesService;

    /**
     * 获取当前菜单的权限数据
     * @param menuId
     * @return
     */
    @ApiOperation(value = "获取当前菜单权限数据接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "menuId",value = "菜单id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("powers/getPowersData")
    public CommonResult getPowersData(String menuId){

        List<TransferDto> allRoleData = sysrolesService.list().stream().map(sysroles -> {
            TransferDto transferDto = new TransferDto();
            transferDto.setValue(sysroles.getTid());
            transferDto.setTitle(sysroles.getName());
            return transferDto;
        }).collect(Collectors.toList());

        QueryWrapper<Syspowers> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(Syspowers::getRoleid)
                .eq(Syspowers::getMenuid,menuId);
        List<Syspowers> list = syspowersService.list(queryWrapper);
        List<String> allocatedRoleList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            allocatedRoleList = list.stream().map(Syspowers::getRoleid).collect(Collectors.toList());
        }
        Map<String,Object> map  = new HashMap<>(2);
        map.put("allData",allRoleData);
        map.put("allocatedData",allocatedRoleList);
        return CommonResult.success(map);
    }

    /**
     * 更新权限分配
     * @return
     */
    @ApiOperation(value = "更新权限分配接口")
    @PostMapping("powers/updatePowers")
    public CommonResult updatePowers(PowersVo powersVo){

        Boolean flag =  syspowersService.updatePowers(powersVo.getMenuId(),powersVo.getRoleIds());
        if(!flag){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }
}
