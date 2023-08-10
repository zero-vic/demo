package com.hy.sys.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/08/09
 * Description: SpringSecurity配置
 * Version:V1.0
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userServiceImpl;

    @Setter
    private List<String> ignoreUrls;

    public WebSecurityConfig(UserDetailsService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (CollectionUtil.isEmpty(ignoreUrls)) {
            ignoreUrls = Arrays.asList("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs","/rsa/publicKey","/oauth/**");
        }

        log.info("whitelist path:{}", JSONUtil.toJsonStr(ignoreUrls));

        http
                .authorizeRequests()
                .antMatchers(Convert.toStrArray(ignoreUrls)).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 用户名密码认证授权提供者
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        // 是否隐藏用户不存在异常，默认:true-隐藏；false-抛出异常；
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }


    /**
     * 密码编码器
     * <p>
     * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀->标识BCYPT算法加密；{noop}->标识不使用任何加密即明文的方式
     * 密码判读 DaoAuthenticationProvider#additionalAuthenticationChecks
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
