package com.hy.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/24 16:12
 **/
@SpringBootApplication(scanBasePackages = {"com.hy.demo"})
@EnableDiscoveryClient
@MapperScan(value = "com.hy.demo.mapper")
@EnableFeignClients(basePackages = {"com.hy.demo"})
public class DemoApplication {
    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        log.info("info 测试日志{}","dev环境配置");
        log.debug("debug 测试日志{}","prod环境配置");

    }
}
