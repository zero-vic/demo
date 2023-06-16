package com.xy.cloud.demo.test.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xy.cloud.demo.entity.Audit;
import com.xy.cloud.demo.entity.User;
import com.xy.cloud.demo.entity.Zbmb4Infos;
import com.xy.cloud.demo.service.IAuditService;
import com.xy.cloud.demo.service.IUserService;
import com.xy.cloud.demo.service.IZbmb4InfosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 14:26
 **/
@RestController
@Api(tags = {"测试接口管理"})
public class TestController {

    @Reference(version = "1.0.0",check = false)
    private IUserService userService;

    @Reference(version = "1.0.0", check = false)
    private IAuditService auditService;
    @Reference(version = "1.0.0",check = false)
    private IZbmb4InfosService zbmb4InfosService;

    @ApiOperation(value = "测试方法")
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "测试id",required = true,paramType = "path",dataType = "String",defaultValue = "1554556561262")})
    @GetMapping("/test/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "测试Audit方法pg")
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "测试id",required = true,paramType = "path",dataType = "Integer",defaultValue = "3")})
    @GetMapping("/testAudit/{id}")
    public Audit getAudit(@PathVariable Integer id){
        return auditService.getById(id);
    }
    @ApiOperation(value = "测试分页方法pg")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5")})
    @GetMapping("/list/{pageNum}/{pageSize}")
    public IPage<Zbmb4Infos> getList(@PathVariable("pageNum") Long pageNum,@PathVariable("pageSize") Long pageSize){

        return zbmb4InfosService.getList(pageNum, pageSize);
    }


}
