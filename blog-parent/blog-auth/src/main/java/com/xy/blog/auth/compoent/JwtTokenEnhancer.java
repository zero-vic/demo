package com.xy.blog.auth.compoent;


import com.xy.blog.auth.domain.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description jwt内容增强器
 * @Author yao
 * @Date 2023/5/25 9:03
 **/
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        SecurityUser securityUser = (SecurityUser) oAuth2Authentication.getPrincipal();
        // 把用户id添加到jwt中
        Map<String,Object> info = new HashMap<>();
        info.put("id",securityUser.getId());
        info.put("client_id",securityUser.getClientId());
        info.put("roleid",securityUser.getRoleId());
        info.put("roletype",securityUser.getRoleType());
        info.put("unitid",securityUser.getUnitId());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
