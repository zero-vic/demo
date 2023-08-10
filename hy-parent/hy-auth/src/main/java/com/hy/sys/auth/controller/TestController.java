package com.hy.sys.auth.controller;

import com.hy.sys.common.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * description:
 * yao create 2023年08月10日
 * version: 1.0
 */
@RestController
@RequestMapping("/oauth")
public class TestController {

    @GetMapping("test")
    public CommonResult test(){
        return CommonResult.success(1);
    }
}
