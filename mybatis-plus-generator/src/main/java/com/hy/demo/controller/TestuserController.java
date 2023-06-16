package com.hy.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.common.CommonPage;			
import com.hy.demo.common.CommonResult;
import com.hy.demo.service.TestuserService;
import com.hy.demo.entity.Testuser;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xy
 * @since 2023-05-29
 */
@RestController
@Api(tags="TestuserController" ,description = "Testuser接口管理")
@RequestMapping("/testuser")
public class TestuserController {


    @Autowired
    private TestuserService testuserService;


    @ApiOperation(value = "列表分页接口")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5")})
    @GetMapping(value = "/{pageNum}/{pageSize}")
    public CommonResult<CommonPage<Testuser>> list(@PathVariable("pageNum") Long pageNum, @PathVariable("pageSize") Long pageSize) {
        if (pageNum == null) {
            pageNum = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
	return CommonResult.success(new CommonPage<>(testuserService.page(new Page<>(pageNum,pageSize),new QueryWrapper<>())));
    }

    @ApiOperation(value = "根据id查询接口")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键Id", required = true, dataType = "String")
    @GetMapping(value = "/{id}")
    public CommonResult<Testuser> getById(@PathVariable("id") String id) {
    	return CommonResult.success(testuserService.getById(id));
    }
    @ApiOperation(value = "新增接口")
    @PostMapping(value = "/create")
    public CommonResult<Testuser> create(@RequestBody Testuser params) {
        testuserService.save(params);
	return CommonResult.success();
    }
    @ApiOperation(value = "根据id删除接口")
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键Id", required = true, dataType = "String")
    @PostMapping(value = "/delete/{id}")
    public CommonResult<Testuser> delete(@PathVariable("id") String id) {
        testuserService.removeById(id);
        return CommonResult.success();
    }
     @ApiOperation(value = "更新接口")
    @PostMapping(value = "/update")
    public CommonResult<Testuser> update(@RequestBody Testuser params) {
        testuserService.updateById(params);
        return CommonResult.success();
    }
}
