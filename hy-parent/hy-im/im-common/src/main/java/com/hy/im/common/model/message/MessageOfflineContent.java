package com.hy.im.common.model.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName MessageOfflineContent
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Data
public class MessageOfflineContent {

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
