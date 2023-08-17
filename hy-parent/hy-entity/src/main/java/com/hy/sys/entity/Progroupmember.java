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
 * 群成员表
 * </p>
 *
 * @author yao
 * @since 2023-08-15
 */
@TableName("pro_group_member")
@Data
public class Progroupmember implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "tid",type = IdType.ASSIGN_UUID)
    private String tid;

    /**
     * 用户id
     */
    @TableField("yhid")
    private String yhid;

    /**
     * 群id
     */
    @TableField("qid")
    private String qid;

    /**
     * 群角色 1群主，2管理员，普通成员
     */
    @TableField("qjs")
    private Integer qjs;

    /**
     * 群昵称
     */
    @TableField("qnc")
    private String qnc;

    /**
     * 创建时间
     */
    @TableField("cjsj")
    private LocalDateTime cjsj;


}
