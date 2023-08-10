package com.hy.sys.kefu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @ClassName KefuApplication
 * description:
 * yao create 2023年07月19日
 * version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.hy.sys"})
@EnableDiscoveryClient
@MapperScan(value = "com.hy.sys.mapper")
@EnableFeignClients(basePackages = {"com.hy.sys.kefu.feignclient"})
@EnableAsync
public class KefuApplication {
    public static void main(String[] args) {
        SpringApplication.run(KefuApplication.class,args);
    }
}
