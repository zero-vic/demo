package com.hy.demo.feignclient; /**
 * Description：
 * yao create 2023/6/9 9:48
 **/

import com.hy.demo.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 *  @ClassName AuthService
 *  description:
 *  yao create 2023年06月09日
 *  version: 1.0
 */
@FeignClient("demo-oauth2-auth")
public interface AuthService {

    @PostMapping(value = "/oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String, String> parameters);
}
