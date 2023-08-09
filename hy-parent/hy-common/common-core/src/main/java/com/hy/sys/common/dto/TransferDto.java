package com.hy.sys.common.dto;

import lombok.Data;

/**
 * @ClassName RolePowersDto
 * description:layui穿梭框的数据格式
 * yao create 2023年08月01日
 * version: 1.0
 */
@Data
public class TransferDto {
    /**
     * 数据值
     */
    private String value;
    /**
     * 数据标题
     */
    private String title;
    /**
     * 是否禁用
     */
    private String disabled;
    /**
     * 是否选中
     */
    private String checked;

}
