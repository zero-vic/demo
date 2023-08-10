package com.hy.sys.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageOffline {

    private String siteId;

    private Integer appId;

    private String postId;

    private String receiveId;

    private String msg;

    private LocalDateTime sendtime;

    private Integer msgtype;

    private Integer isdel;

    private String messageId;

    private Integer conversationType;

    private String conversationId;

    private Long messageSequence;
}