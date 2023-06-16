package com.hy.demo.gateway.authorization;

import cn.hutool.core.collection.CollUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hy.demo.constants.AuthConstant;
import com.hy.demo.dto.UserJwtDto;
import com.hy.demo.gateway.config.IgnoreUrlsConfig;
import com.hy.demo.service.SysmenuService;
import com.nimbusds.jose.JWSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.*;

/**
 *
 * @Description 鉴权管理器，用于判断是否有资源的访问权限
 * @Author yao
 * @Date 2023/5/25 11:30
 **/
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SysmenuService sysmenuService;
//    private Map<String, List<String>> resourceRolesMap;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

//    @PostConstruct
//    public void initData() {
//        resourceRolesMap = new TreeMap<>();
//        resourceRolesMap.put("/test-api/test", CollUtil.toList("ADMIN"));
//        resourceRolesMap.put("/test-api/user/test/**", CollUtil.toList("ADMIN", "TEST"));
//        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
//    }

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
            // 管理员拥有所有权限直接放行
            if (AuthConstant.ADMIN_ROLE_TYPE.equals(userJwtDto.getRoletype())) {
                return Mono.just(new AuthorizationDecision(true));
            }

            if (AuthConstant.ADMIN_CLIENT_ID.equals(userJwtDto.getClientId()) && !pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(false));
            }
            // 访问路径是否有权限
            List<String> paths = sysmenuService.getPathByRoleId(userJwtDto.getRoleid());

            if (CollUtil.isEmpty(paths)){
                return Mono.just(new AuthorizationDecision(false));
            }
            for (String path : paths) {
                if (pathMatcher.match(path, uri.getPath())) {
                    return Mono.just(new AuthorizationDecision(true));
                }
            }

//            if (AuthConstant.PORTAL_CLIENT_ID.equals(userDto.getClientId()) && pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
        } catch (ParseException e) {
            e.printStackTrace();
            return Mono.just(new AuthorizationDecision(false));
        }
        //非管理端路径直接放行
//        if (!pathMatcher.match(AuthConstant.ADMIN_URL_PATTERN, uri.getPath())) {
//            return Mono.just(new AuthorizationDecision(true));
//        }
        //管理端路径需校验权限
        // 暂不进行角色权限校验
        // 角色权限实现
        // Map<Object, Object> resourceRolesMap = redisTemplate.opsForHash().entries(AuthConstant.RESOURCE_ROLES_MAP_KEY);
//        Iterator<String> iterator = resourceRolesMap.keySet().iterator();
//        List<String> authorities = new ArrayList<>();
//        while (iterator.hasNext()) {
//            String pattern = iterator.next();
//            if (pathMatcher.match(pattern, uri.getPath())) {
//                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
//            }
//        }
//        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //否者不让放行
        return Mono.just(new AuthorizationDecision(false));
//        List<String> authorities = CollUtil.toList(AuthConstant.DEFAULT_ROLE);
//        //认证通过且角色匹配的用户可访问当前路径
//        return mono
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(authorities::contains)
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
