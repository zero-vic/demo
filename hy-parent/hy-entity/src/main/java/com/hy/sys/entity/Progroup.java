package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 群组表
 * </p>
 *
 * @author yao
 * @since 2023-08-15
 */
@TableName("pro_group")
@Data
public class Progroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群id
     */
    @TableId(value = "tid",type = IdType.ASSIGN_UUID)
    private String tid;

    /**
     * 站点id
     */
    @TableField("wzid")
    private String wzid;

    /**
     * 群名称
     */
    @TableField("qmc")
    private String qmc;

    /**
     * 群头像
     */
    @TableField("qtx")
    private String qtx;

    /**
     * 群描述
     */
    @TableField("qms")
    private String qms;

    /**
     * 群公告
     */
    @TableField("qgg")
    private String qgg;

    /**
     * 群状态 0正常 1解散
     */
    @TableField("qzt")
    private Integer qzt;

    /**
     * 创建时间
     */
    @TableField("cjsj")
    private LocalDateTime cjsj;

    /**
     * 修改时间
     */
    @TableField("xgsj")
    private LocalDateTime xgsj;

    /**
     * 是否禁言 0 不禁言 1 全体禁言
     */
    @TableField("sfjy")
    private Integer sfjy;


}
