package com.hy.sys.kefu.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName ConversationVo
 * description:
 * yao create 2023年07月25日
 * version: 1.0
 */
@Data
public class ConversationVo {

    private String tid;


    private String postid;


    private String receiveid;


    private String siteid;


    private Integer type;


    private Integer istop;


    private LocalDateTime addtime;

    private long unreadCount;

    private String name;
}
