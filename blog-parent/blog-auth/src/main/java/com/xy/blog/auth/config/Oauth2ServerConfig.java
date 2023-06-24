package com.xy.blog.auth.config;


import com.xy.blog.auth.compoent.JwtTokenEnhancer;
import com.xy.blog.auth.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 认证服务器配置
 * @Author yao
 * @Date 2023/5/24 18:08
 **/
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
        private final PasswordEncoder passwordEncoder;
        private final UserServiceImpl userDetailsService;
        private final AuthenticationManager authenticationManager;
        private final JwtTokenEnhancer jwtTokenEnhancer;

        /**
         *
         * 令牌访问安全约束配置
         **/
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
                //表单模式申请令牌，表示支持 client_id 和 client_secret 做登录认证
                security
                        .tokenKeyAccess("permitAll()") //对应/oauth/token_key 公开，获取公钥需要访问该端点
                        .checkTokenAccess("permitAll()") //对应/oauth/check_token ，路径公开，校验Token需要请求该端点
                        .allowFormAuthenticationForClients();

        }
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
                        .secret(this.passwordEncoder.encode("123456"))
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
        @Bean
        public AuthorizationCodeServices authorizationCodeServices(){
                //基于内存存储的的授权码服务
                return new InMemoryAuthorizationCodeServices();
                //基于内存存储的的授权码服务
//                return new JdbcAuthorizationCodeServices(dataSource);
        }
        /**
         *
         *  令牌访问端点配置
         **/
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
                List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
                tokenEnhancers.add(jwtTokenEnhancer);
                tokenEnhancers.add(accessTokenConverter());
                //配置jwt内容增强
                tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
                endpoints.authenticationManager(authenticationManager)
                        //配置加载用户信息配置
                        .userDetailsService(userDetailsService)
                        /**
                         *
                         * refresh_token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                         * 1.重复使用：access_token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                         * 2.非重复使用：access_token过期刷新时， refresh_token过期时间延续，在refresh_token有效期内刷新而无需失效再次登录
                         */
//                        .reuseRefreshTokens(false)
                        .accessTokenConverter(accessTokenConverter())
                        .tokenEnhancer(tokenEnhancerChain)
                        //授权码模式服务
                        .authorizationCodeServices(authorizationCodeServices());

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
