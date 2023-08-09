package com.hy.sys.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.hy.sys"})
@EnableDiscoveryClient
//@MapperScan(value = "com.hy.demo.mapper")
public class HyAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyAuthApplication.class, args);
    }

}
