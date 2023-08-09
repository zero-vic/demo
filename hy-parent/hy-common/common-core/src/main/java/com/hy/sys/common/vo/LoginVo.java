package com.hy.sys.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/30 16:08
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginVo {
    @NotNull(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @NotNull(message = "密码不能为空")
    @Min(value = 6,message = "密码不能低于6位数")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @NotNull(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
    @NotNull()
    @ApiModelProperty(value = "验证唯一标识", required = true)
    private String codeid;
}
