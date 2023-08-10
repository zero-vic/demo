package com.hy.sys.kefu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.sys.common.annotation.SysLog;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.constants.SystemConstant;
import com.hy.sys.common.enums.BusinessType;
import com.hy.sys.common.enums.DelFlagEnum;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import com.hy.sys.common.vo.LoginVo;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.kefu.feignclient.AuthService;
import com.hy.sys.service.SyspersonsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SysloginController
 * description:
 * yao create 2023年08月03日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "登陆接口管理", tags = {"SysloginController"})
public class SysloginController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SyspersonsService syspersonsService;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "登陆接口")
    @PostMapping("login")
    @SysLog(model = "登录", type = BusinessType.LOGIN)
    public CommonResult systemLogin(@Valid LoginVo loginVo) {
        // 校验 验证码
        String key = SystemConstant.REDIS_CAPTCHA_PREFIX + loginVo.getCodeid();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(key))) {
            return CommonResult.failed(ResultCode.CAPTCHA_EXPIRE);
        }
        String code = stringRedisTemplate.opsForValue().get(key);
        assert code != null;
        if (!code.equals(loginVo.getCode())) {
            return CommonResult.failed(ResultCode.CAPTCHA_ERROR);
        }
        // 校验用户名和密码
        QueryWrapper<Syspersons> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Syspersons::getAccount, loginVo.getUsername())
                .eq(Syspersons::getIsdel, DelFlagEnum.NORMAL.getCode());
        long count = syspersonsService.count(queryWrapper);
        if (count < 1) {
            return CommonResult.failed(ResultCode.USERNAME_PWD_ERROR);
        }
        // 获取token
        Map<String, String> params = new HashMap<>();

        params.put("client_id", SystemConstant.CLIENT_ID);
        params.put("client_secret", SystemConstant.CLIENT_SECRET);
        params.put("grant_type", SystemConstant.GRANT_TYPE_PASSWORD);
        params.put("username", loginVo.getUsername());
        params.put("password", loginVo.getPassword());
        CommonResult res = authService.getAccessToken(params);
        if(res.getCode() == 200){
            /**
             * 登出使用的黑名单，所以在登录的时候需要吧黑名单的token删了
             */
            String token = JSONUtil.parseObj(res.getData()).getStr("token");
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JSONObject payloads = JWTUtil.parseToken(realToken).getPayloads();
            // jwt 唯一标识
            String jti = payloads.getStr("jti");
            stringRedisTemplate.delete(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
        }
        return res;
    }

    @ApiOperation(value = "刷新token接口")
    @PostMapping("refreshToken")
    public CommonResult refreshtoken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            return CommonResult.failed("token 为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", SystemConstant.CLIENT_ID);
        params.put("client_secret", SystemConstant.CLIENT_SECRET);
        params.put("grant_type", SystemConstant.GRANT_TYPE_REFRESH);
        params.put("refresh_token", refreshToken);
        params.put("username", "");
        params.put("password", "");
        return authService.getAccessToken(params);
    }

    @ApiOperation(value = "获取验证码的接口")
    @GetMapping("login/getCaptcha")
    public CommonResult getCaptcha() {
        //定义图形验证码的长和宽
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(120, 50, 4, 4);
        String imageBase64 = captcha.getImageBase64();
        String code = captcha.getCode();
        imageBase64 = SystemConstant.IMAGE_BASE64_PREFIX + imageBase64;
        // code存入redis
        String uuid = UUID.randomUUID().toString();
        String key = SystemConstant.REDIS_CAPTCHA_PREFIX + uuid;
        stringRedisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
        Map<String, Object> map = new HashMap<>(2);
        map.put("captchaId", uuid);
        map.put("base64code", imageBase64);
        return CommonResult.success(map);
    }

    /**
     * 采用黑名单的方式
     * @param request
     * @return
     */
    @ApiOperation("注销登出")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public CommonResult logout(HttpServletRequest request) {
        String jwt = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if (StringUtils.isEmpty(jwt)) {
            return CommonResult.failed(ResultCode.UNAUTHORIZED);
        }

        String realToken = jwt.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
        JSONObject payloads = JWTUtil.parseToken(realToken).getPayloads();
        // jwt 唯一标识
        String jti = payloads.getStr("jti");
        // jwt 失效时间
        long exp = payloads.getLong("exp");
        long currentTimeSeconds = System.currentTimeMillis() / 1000;
        if (exp < currentTimeSeconds) {
            return CommonResult.failed(ResultCode.TOKEN_EXPIRED);
        }
        stringRedisTemplate.opsForValue().set(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti, jti, (exp - currentTimeSeconds), TimeUnit.SECONDS);


        return CommonResult.success();
    }
}
