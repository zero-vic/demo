package com.hy.sys.common.vo;

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
    private String username;
    @NotNull(message = "密码不能为空")
    @Min(value = 6,message = "密码不能低于6位数")
    private String password;
    @NotNull(message = "验证码不能为空")
    private String code;
    @NotNull()
    private String codeid;
}
