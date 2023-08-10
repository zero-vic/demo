package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName Proconversation
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Data
@TableName("pro_conversation")
public class Proconversation {

    @TableId("tid")
    private String tid;

    @TableField("postid")
    private String postid;

    @TableField("receiveid")
    private String receiveid;

    @TableField("siteid")
    private String siteid;

    @TableField("type")
    private Integer type;

    @TableField("istop")
    private Integer istop;

    @TableField("addtime")
    private LocalDateTime addtime;

    @TableField("name")
    private String name;

}
