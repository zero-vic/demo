package com.xy.blog.controller;

import com.xy.blog.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@RestController
@RequestMapping("/sys/res")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    @GetMapping("inner/getResRoleMap/{userId}")
    public Map<String, List<String>> getResRoleMap(@PathVariable String userId){
        return resourceService.getResRoleMap(userId);
    }
}
