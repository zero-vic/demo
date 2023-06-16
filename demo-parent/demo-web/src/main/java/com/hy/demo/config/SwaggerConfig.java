package com.hy.demo.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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
@EnableKnife4j
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.hy.demo.controller")
                .title("web测试")
                .description("web测试相关接口文档")
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
