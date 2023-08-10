package com.hy.sys.auth.config;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.hy.sys.auth.filter.CustomClientCredentialsTokenEndpointFilter;
import com.hy.sys.auth.service.UserServiceImpl;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/08/09
 * Description: 认证服务器配置
 * Version:V1.0
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenEnhancer jwtTokenEnhancer;

    private RedisConnectionFactory redisConnectionFactory;



    /**
     * 配置客户端详情服务
     **/
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用内存模式存储
        clients.inMemory()
                //指定客户端唯一id
                .withClient("test-id")
                //客户端密钥 后面改成配置文件加载
                .secret(this.passwordEncoder.encode("12345611"))
                .redirectUris("http://www.baidu.com") //配置redirect_uri，用于授权成功后跳转
                // 定义客户端权限
                .scopes("all")
                //指定授权模式 授权模式一共四种 1.authorization_code 2.password 3.implicit 4.client_credentials
                .authorizedGrantTypes("password", "refresh_token","authorization_code")
                // accessToken 过期时间
                .accessTokenValiditySeconds(3600*24)
                // refreshToken 过期时间
                .refreshTokenValiditySeconds(3600*24*7);
        // .and().whthClient() 配置其他客户端授权

    }
    /**
     *
     * 令牌访问安全约束配置
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        CustomClientCredentialsTokenEndpointFilter endpointFilter = new CustomClientCredentialsTokenEndpointFilter(security);
        endpointFilter.afterPropertiesSet();
        endpointFilter.setAuthenticationEntryPoint(authenticationEntryPoint());
        security.addTokenEndpointAuthenticationFilter(endpointFilter);
        security.authenticationEntryPoint(authenticationEntryPoint())
                .allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
        //表单模式申请令牌，表示支持 client_id 和 client_secret 做登录认证
//                security
//                        .tokenKeyAccess("permitAll()") //对应/oauth/token_key 公开，获取公钥需要访问该端点
//                        .checkTokenAccess("permitAll()") //对应/oauth/check_token ，路径公开，校验Token需要请求该端点
//                        .allowFormAuthenticationForClients();

    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // Token增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(jwtTokenEnhancer);
        tokenEnhancers.add(accessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        //token存储模式设定 默认为InMemoryTokenStore模式存储到内存中
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService)
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .reuseRefreshTokens(true)
                .tokenStore(redisTokenStore());

    }

    /**
     * 自定义认证异常响应数据
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            CommonResult<?> result = CommonResult.failed(ResultCode.CLIENT_AUTHENTICATION_FAILED);
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }



    /**
     * 使用redis对令牌进行读写
     * @return
     */
    @Bean
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    /**
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }
    /**
     *
     *   需要配置加载用户信息的服务UserServiceImpl及RSA的钥匙对KeyPair
     **/
    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "123456".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "123456".toCharArray());
    }
}
