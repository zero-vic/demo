package com.hy.sys.gateway;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.hy.sys"})
@EnableDiscoveryClient
@MapperScan(value = "com.hy.sys.mapper")
@Slf4j
public class HyGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HyGatewayApplication.class, args);
        log.info("\n" +
                "  _  ___   __    ___   _ _____ _____      _____   __\n" +
                " | || \\ \\ / /__ / __| /_\\_   _| __\\ \\    / /_\\ \\ / /\n" +
                " | __ |\\ V /___| (_ |/ _ \\| | | _| \\ \\/\\/ / _ \\ V / \n" +
                " |_||_| |_|     \\___/_/ \\_\\_| |___| \\_/\\_/_/ \\_\\_|  \n" +
                "                                                    ");
        log.info("gateway启动完成");
    }

}
