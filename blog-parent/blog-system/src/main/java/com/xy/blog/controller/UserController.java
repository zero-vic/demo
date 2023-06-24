package com.xy.blog.controller;

import com.xy.blog.common.dto.UserDto;
import com.xy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/inner/loadUserByUsername/{username}")
    public UserDto loadUserByUsername(@PathVariable String username){
        return userService.loadUserByUsername(username);
    }

}
