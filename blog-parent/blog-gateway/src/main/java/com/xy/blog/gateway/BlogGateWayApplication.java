package com.xy.blog.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *  @ClassName BlogGateWayApplication
 *  description:
 *  yao create 2023年06月21日
 *  version: 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.xy.blog"})
@EnableDiscoveryClient
public class BlogGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogGateWayApplication.class,args);
    }
}
