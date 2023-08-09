package com.hy.sys.common.dto;

import lombok.Data;

/**
 * @ClassName MenuTreeDto
 * description:
 * yao create 2023年07月28日
 * version: 1.0
 */
@Data
public class MenuTreeDto {
    /**
     * id
     */
    private String id;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 展示内容
     */
    private String title;

}
