package com.hy.sys.kefu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Syslog;
import com.hy.sys.kefu.vo.PageParam;
import com.hy.sys.service.SyslogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SyslogController
 * description:
 * yao create 2023年07月31日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "系统日志管理接口",tags = {"SyslogController"})
public class SyslogController {

    @Autowired
    private SyslogService syslogService;

    @ApiOperation(value = "获取日志列表接口")
    @PostMapping("syslog/GetListPage")
    public CommonResult getListPage(PageParam pageParam){
        IPage<Syslog> page = new Page<>(pageParam.getPage(),pageParam.getLimit());
        QueryWrapper<Syslog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sl.adddate");
        if(StringUtils.isNotBlank(pageParam.getCondition())){
            queryWrapper.like("sl.user",pageParam.getCondition());
        }
        IPage<Syslog> iPage = syslogService.getListPage(page,queryWrapper);
        return CommonResult.success(new CommonPage<>(iPage));
    }
}
