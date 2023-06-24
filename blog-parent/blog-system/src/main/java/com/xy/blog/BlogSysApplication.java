package com.xy.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/23
 * Description:
 * Version:V1.0
 */
@SpringBootApplication(scanBasePackages = {"com.xy.blog"})
public class BlogSysApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogSysApplication.class,args);
    }
}
