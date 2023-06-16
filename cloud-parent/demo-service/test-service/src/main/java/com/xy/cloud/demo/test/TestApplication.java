package com.xy.cloud.demo.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 11:27
 **/
@SpringBootApplication(scanBasePackages = {"com.xy.cloud.demo"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.xy.cloud.demo.test.mapper"})
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
