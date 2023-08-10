package com.hy.sys.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.hy.demo.mapper")
public class XxlJobServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobServiceApplication.class, args);
    }

}
