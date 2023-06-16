package com.xy.cloud.demo.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description web启动类
 * @Author yao
 * @Date 2023/5/23 12:16
 **/
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = {"com.xy.cloud.demo"})
@EnableDiscoveryClient
public class TestWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestWebApplication.class, args);
    }
}
