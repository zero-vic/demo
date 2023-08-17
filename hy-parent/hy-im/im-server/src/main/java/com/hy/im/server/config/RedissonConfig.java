package com.hy.im.server.config;

import cn.hutool.core.util.StrUtil;
import io.micrometer.core.instrument.util.StringUtils;
import io.netty.bootstrap.BootstrapConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁 Redisson 配置
 *
 */

@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${redisson.address}")
    private String address;
    @Value("${redisson.password}")
    private String password;
    @Value("${redisson.database}")
    private Integer database;
    @Value("${redisson.poolMinIdle}")
    private Integer poolMinIdle;
    @Value("${redisson.poolConnTimeout}")
    private int poolConnTimeout;
    @Value("${redisson.poolSize}")
    private Integer poolSize;


    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();
        String node = address.startsWith("redis://") ? address : "redis://" + address;
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(node);
        singleServerConfig.setDatabase(database);
        singleServerConfig.setConnectionMinimumIdleSize(poolMinIdle);
        singleServerConfig.setConnectionPoolSize(poolSize);
        singleServerConfig.setConnectTimeout(poolConnTimeout);
        if (StrUtil.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        StringCodec stringCodec = new StringCodec();
        config.setCodec(stringCodec);
        return Redisson.create(config);
    }

}
