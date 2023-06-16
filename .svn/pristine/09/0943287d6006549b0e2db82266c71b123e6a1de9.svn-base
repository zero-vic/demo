package com.hy.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.demo.entity.User;
import com.hy.demo.mapper.UserMapper;
import com.hy.demo.service.ITestService;
import com.hy.demo.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class DemoShardingsphereApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITestService testService;

    @Test
    void contextLoads() {
//        for (int i = 0; i <10;i++ ) {
//            User user = new User();
//            user.setUserName("sharding");
//            user.setGroupId(1L);
//            user.setPassword("123456");
//            user.setPhoneNumber("123456");
//            userService.save(user);
//
//        }
    }
    @Test
    void getUserById(){
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        for (int i = 0; i <5;i++ ) {
//            User user = new User();
//            LocalDateTime localDateTime = LocalDateTime.now().plusMonths(-1);
//            user.setCTime(localDateTime);
//            user.setUserName("sharding"+i);
//            userService.save(user);
//        }
        IPage<User> page = new Page<>(2L,10L);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().gt(User::getCTime,LocalDateTime.now().plusDays(-20));
        IPage<User> iPage = userService.page(page, queryWrapper);
//        System.out.println(iPage.toString());
//        System.out.println(iPage.getRecords());
//        List<User> list = userService.list(queryWrapper);
        System.out.println("---------------:"+iPage.getRecords());

    }
    @Test
    void dynamicContext(){
        Long id = 123456L;
        com.hy.demo.entity.Test masterTest = testService.getMasterTest(id);
        com.hy.demo.entity.Test slaveTest = testService.getSlaveTest(id);
        System.out.println("___________________________________");
        System.out.println(masterTest);
        System.out.println(slaveTest);
    }
    @Test
    void shardingPage(){
        IPage<User> page = userService.getList(1L, 10L);
        System.out.println("___________________________________");
        System.out.println("--------------------"+page.getRecords());
    }

    @Test
    void addSharding(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i <5;i++ ) {
            User user = new User();
            LocalDateTime localDateTime = LocalDateTime.now().plusMonths(-1);
            user.setCTime(localDateTime);
            user.setUserName("shardingTe"+i);
            userService.save(user);
        }
    }

}
