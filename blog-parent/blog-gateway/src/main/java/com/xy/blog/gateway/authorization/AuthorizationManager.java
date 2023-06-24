package com.xy.blog.gateway.authorization;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.xy.blog.common.constants.AuthConstant;
import com.xy.blog.common.dto.UserJwtDto;
import com.xy.blog.gateway.config.IgnoreUrlsConfig;
import com.xy.blog.gateway.feignclient.ResourceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @Description 鉴权管理器，用于判断是否有资源的访问权限
 * @Author yao
 * @Date 2023/5/25 11:30
 **/
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private ResourceFeignClient resourceFeignClient;




    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }
        //对应跨域的预检请求直接放行
        if(request.getMethod()==HttpMethod.OPTIONS){
            return Mono.just(new AuthorizationDecision(true));
        }

        //不同用户体系登录不允许互相访问
        try {
            String token = request.getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
            if(StrUtil.isEmpty(token)){
                return Mono.just(new AuthorizationDecision(false));
            }
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userStr = jwsObject.getPayload().toString();

            UserJwtDto userJwtDto = JSONUtil.toBean(userStr, UserJwtDto.class);

            if (AuthConstant.ADMIN_CLIENT_ID.equals(userJwtDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
            Map<String, List<String>> resRoleMap = resourceFeignClient.getResRoleMap(userJwtDto.getId());
            Iterator<String> iterator = resRoleMap.keySet().iterator();
            List<String> authorities = new ArrayList<>();
            while (iterator.hasNext()) {
                String pattern = iterator.next();
                if (pathMatcher.match(pattern, uri.getPath())) {
                    authorities.addAll(Convert.toList(String.class, resRoleMap.get(pattern)));
                }
            }
            authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
            return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }

    }

}
