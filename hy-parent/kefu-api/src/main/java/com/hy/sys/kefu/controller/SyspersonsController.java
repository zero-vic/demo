package com.hy.sys.kefu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.enums.DelFlagEnum;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.utils.BCryptUtils;
import com.hy.sys.common.vo.SyspersonsVo;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.kefu.vo.PageParam;
import com.hy.sys.kefu.vo.SyspersonsParam;
import com.hy.sys.service.SyspersonsService;
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
import java.util.List;
import java.util.UUID;

/**
 * @ClassName SyspersonsController
 * description:
 * yao create 2023年07月26日
 * version: 1.0
 */
@RestController
@RequestMapping("system/user")
@Api(value = "系统用户管理接口",tags = {"SyspersonsController"})
public class SyspersonsController {

    @Autowired
    private SyspersonsService syspersonsService;

    @ApiOperation(value = "获取用户列表接口")
    @PostMapping("GetListPage")
    public CommonResult getListPage(PageParam pageParam){
        IPage<Syspersons> page = new Page<>(pageParam.getPage(), pageParam.getLimit());
        QueryWrapper<Syspersons> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sp.isdel", DelFlagEnum.NORMAL.getCode());
        if(StringUtils.isNotBlank(pageParam.getCondition())){
            queryWrapper.like("sp.name",pageParam.getCondition());
        }
        IPage<SyspersonsVo> iPage = syspersonsService.getListPage(page,queryWrapper);
        return CommonResult.success(new CommonPage<>(iPage));
    }
    @ApiOperation(value = "删除用户接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "用户id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("Delete")
    public CommonResult deleteUser(String id){
        QueryWrapper<Syspersons> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Syspersons::getTid,id)
                .eq(Syspersons::getIsdel,DelFlagEnum.NORMAL.getCode());
        Syspersons person = syspersonsService.getOne(queryWrapper);
        if(person !=null){
            person.setIsdel(DelFlagEnum.DELETE.getCode());
            syspersonsService.updateById(person);
            return CommonResult.success();
        }
        return CommonResult.failed("该用户不存在");
    }

    @ApiOperation(value = "添加用户接口")
    @PostMapping("add")
    public CommonResult addUser(@Valid SyspersonsParam syspersonsParam){
        Syspersons syspersons = new Syspersons();
        BeanUtils.copyProperties(syspersonsParam,syspersons);
        syspersons.setTid(UUID.randomUUID().toString());
        syspersons.setAddtime(LocalDateTime.now());
        syspersons.setPwd(BCryptUtils.encode(syspersons.getPwd()));
        syspersons.setIsdel(DelFlagEnum.NORMAL.getCode());
        boolean save = syspersonsService.save(syspersons);
        if(!save){
            return CommonResult.failed(ErrorMsgConstant.ADD_ERROR);
        }
        return CommonResult.success();
    }
    @ApiOperation(value = "获取用户详情接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "用户id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("Details")
    public CommonResult getUserDetail(String id){
        Syspersons syspersons = syspersonsService.getUserById(id);
        SyspersonsParam syspersonsVo = new SyspersonsParam();
        BeanUtils.copyProperties(syspersons,syspersonsVo);
        return CommonResult.success(syspersonsVo);
    }
    @ApiOperation(value = "更新用户接口接口")
    @PostMapping("update")
    public CommonResult updateUser(@Valid SyspersonsParam syspersonsParam){
        Syspersons syspersons = new Syspersons();
        BeanUtils.copyProperties(syspersonsParam,syspersons);
        boolean b = syspersonsService.updateById(syspersons);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }

    /**
     * 根据unitid获取相关用户
     * @param unitid
     */
    @ApiOperation(value = "获取机构的用户列表接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "unitid",value = "机构id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("getUserByUnitId")
    public CommonResult getUserByUnitId(String unitid){
        List<TransferDto> allData = syspersonsService.getUserByUnitId(unitid);

        return CommonResult.success(allData);
    }
}
