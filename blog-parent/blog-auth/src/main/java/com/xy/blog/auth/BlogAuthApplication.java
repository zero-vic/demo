package com.xy.blog.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *  @ClassName BlogAuthApplication
 *  description:
 *  yao create 2023年06月21日
 *  version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.xy.blog"})
@EnableDiscoveryClient
@EnableFeignClients
public class BlogAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAuthApplication.class,args);
    }
}
