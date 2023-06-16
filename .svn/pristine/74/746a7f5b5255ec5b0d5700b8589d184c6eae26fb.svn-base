package com.hy.demo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/30 16:08
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginVo {
    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
