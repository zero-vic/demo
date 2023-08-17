package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息记录表
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@TableName("pro_msgrecord")
@Data
public class Promsgrecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 站点ID
     */
    @TableField("webid")
    private String webid;

    /**
     * 发送者ID
     */
    @TableField("postid")
    private String postid;

    /**
     * 接收者ID
     */
    @TableField("receiveid")
    private String receiveid;

    /**
     * 群组ID（暂时不用）
     */
    @TableField("groupid")
    private String groupid;

    /**
     * 消息内容
     */
    @TableField("msg")
    private String msg;

    /**
     * 发送时间
     */
    @TableField("sendtime")
    private LocalDateTime sendtime;

    /**
     * 删除标识
     */
    @TableField("isdel")
    private Integer isdel;

    /**
     * 消息类型 1文本 2图片 
     */
    @TableField("msgtype")
    private Integer msgtype;

}
