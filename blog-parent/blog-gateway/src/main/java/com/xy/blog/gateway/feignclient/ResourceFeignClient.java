package com.xy.blog.gateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@FeignClient(value = "blog-sys")
public interface ResourceFeignClient {

    @GetMapping("/sys/res/inner/getResRoleMap/{userId}")
    Map<String, List<String>> getResRoleMap(@PathVariable String userId);
}
