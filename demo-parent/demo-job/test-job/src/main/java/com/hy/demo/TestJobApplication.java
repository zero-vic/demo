package com.hy.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  @ClassName TestJobApplication
 *  description: 测试job启动类
 *  yao create 2023年06月13日
 *  version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.hy.demo"})
@MapperScan(value = "com.hy.demo.mapper")
public class TestJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestJobApplication.class,args);
    }
}
