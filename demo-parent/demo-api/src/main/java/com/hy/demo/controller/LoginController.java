package com.hy.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.demo.common.CommonResult;
import com.hy.demo.common.ResultCode;
import com.hy.demo.constants.AuthConstant;
import com.hy.demo.entity.Syspersons;
import com.hy.demo.exception.BusinessException;
import com.hy.demo.feignclient.AuthService;
import com.hy.demo.service.SyspersonsService;
import com.hy.demo.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/30 16:05
 **/
@RestController
@Api(tags = {"LoginController"},description = "登陆相关测试接口")
public class LoginController {
    @Autowired
    private AuthService authService;

    @Autowired
    private SyspersonsService syspersonsService;
    @ApiOperation(value = "登录以后返回token")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public CommonResult login(@Valid @RequestBody LoginVo loginVo) {
        int count = syspersonsService.count(new QueryWrapper<Syspersons>().lambda().eq(Syspersons::getName, loginVo.getUsername()));
        if(count<1){
            return CommonResult.failed(ResultCode.USERNAME_PWD_ERROR);
        }

        Map<String, String> params = new HashMap<>();
        params.put("client_id", "test-id");
        params.put("client_secret", "123456");
        params.put("grant_type", "password");
        params.put("username", loginVo.getUsername());
        params.put("password", loginVo.getPassword());
        CommonResult restResult = authService.getAccessToken(params);
        return restResult;
    }
}
