package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hy.sys.config.CustomLocatDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_roles")
@Data
public class Sysroles implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId("tid")
    private String tid;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 说明
     */
    @TableField("description")
    private String description;

    /**
     * 添加时间
     */
    @TableField("addtime")
    @JsonSerialize(using = CustomLocatDateSerializer.class)
    private LocalDateTime addtime;

    /**
     * 机构ID
     */
    @TableField("unitid")
    private String unitid;

    /**
     * 机构类型
     */
    @TableField("unittype")
    private Integer unittype;


}
