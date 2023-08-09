package com.hy.sys.auth.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @Description Oauth2获取Token返回信息封装
 * @Author yao
 * @Date 2023/5/25 10:44
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Oauth2TokenDto {
//    @ApiModelProperty("访问令牌")
    private String token;
//    @ApiModelProperty("刷令牌")
    private String refreshToken;
//    @ApiModelProperty("访问令牌头前缀")
    private String tokenHead;
//    @ApiModelProperty("有效时间（秒）")
    private int expiresIn;
}