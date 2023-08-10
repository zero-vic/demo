package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 权限配置表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_powers")
@Data
public class Syspowers implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 角色ID
     */
    @TableField("roleid")
    private String roleid;

    /**
     * 菜单ID
     */
    @TableField("menuid")
    private String menuid;

    /**
     * 菜单标识
     */
    @TableField("menucode")
    private String menucode;

    /**
     * 更新时间
     */
    @TableField("addtime")
    private LocalDateTime addtime;

    /**
     * 备注
     */
    @TableField("marks")
    private String marks;


}
