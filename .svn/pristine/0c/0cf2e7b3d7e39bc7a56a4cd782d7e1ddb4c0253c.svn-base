package com.hy.demo.auth.config;


import com.hy.demo.config.BaseSwaggerConfig;
import com.hy.demo.domain.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @Description Swagger API文档相关配置
 * @Author yao
 * @Date 2023/5/26 10:28
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hy.demo.auth.controller")
                .title("demo认证中心")
                .description("demo认证中心相关接口文档")
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
