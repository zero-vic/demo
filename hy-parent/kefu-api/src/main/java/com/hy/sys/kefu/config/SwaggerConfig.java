package com.hy.sys.kefu.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.hy.sys.common.config.BaseSwaggerConfig;
import com.hy.sys.common.domain.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hy.sys.kefu.controller")
                .title("kefu")
                .description("kefu测试相关接口文档")
                .contactName("yao")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }
}