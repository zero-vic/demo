package com.hy.sys.kefu.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName SysunitParam
 * description:
 * yao create 2023年08月07日
 * version: 1.0
 */
@Data
public class SysunitParam {
    /**
     * ID
     */
    private String tid;

    /**
     * 父级ID 暂不使用
     */
    private String parentid;

    /**
     * 机构全称
     */
    @NotNull(message = "机构全称不能为空")
    private String pame;

    /**
     * 简称
     */
    @NotNull(message = "简称不能为空")
    private String shortname;

    /**
     * 代码
     */
    private String code;

    /**
     * 单位类型（1 高投 2造价咨询 3设计）
     */
    private String type;

    /**
     * 联系人
     */
    @NotNull(message = "联系人不能为空")
    private String linkman;

    /**
     * 联系电话
     */
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "联系电话格式错误")
    private String tel;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 区域ID
     */
    private String areaid;

    /**
     * 简介
     */
    private String workrange;

    /**
     * 经度 暂不使用
     */
    private BigDecimal longitude;

    /**
     * 纬度 暂不使用
     */
    private BigDecimal latitude;

    /**
     * 机构类型 暂不使用
     */
    private String uniontype;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;

    /**
     * 状态 0 正常 1  禁用
     */
    private Integer state;

    /**
     * 关联的管理帐号
     */
    @NotNull(message = "管理账号不能为空")
    private String adminid;
    /**
     * 管理账号名
     */
    private String adminname;
}
