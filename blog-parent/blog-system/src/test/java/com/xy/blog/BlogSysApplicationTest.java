package com.xy.blog;

import com.xy.blog.mapper.ResourceMapper;
import com.xy.blog.po.ResourcePo;
import com.xy.blog.service.ResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@SpringBootTest
public class BlogSysApplicationTest {
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private ResourceService resourceService;
    @Test
    void test11(){
        List<ResourcePo> roleResListByUserId = resourceMapper.getRoleResListByUserId("1");
        System.out.println(roleResListByUserId);
    }
    @Test
    void contextLoads() {
        Map<String, List<String>> resRoleMap = resourceService.getResRoleMap("1");
        System.out.println(resRoleMap.toString());

    }
}
