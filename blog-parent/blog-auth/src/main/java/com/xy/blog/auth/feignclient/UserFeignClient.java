package com.xy.blog.auth.feignclient;

import com.xy.blog.common.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@FeignClient(value = "blog-sys")
public interface UserFeignClient {
    @GetMapping("sys/user/inner/loadUserByUsername/{username}")
    UserDto loadUserByUsername(@PathVariable String username);
}
